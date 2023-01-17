package app.vazovsky.healsted.presentation.settings.account

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentSettingsAccountBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.showInfoSnackbar
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject
import timber.log.Timber

/** Экран с редактированием профиля */
@AndroidEntryPoint
class SettingsAccountFragment : BaseFragment(R.layout.fragment_settings_account) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentSettingsAccountBinding::bind)
    private val viewModel: SettingsAccountViewModel by viewModels()

    @Inject lateinit var notificationCore: NotificationCore
    @Inject lateinit var dateFormatter: DateFormatter

    private var account: Account? = null

    override fun callOperations() {
        viewModel.getProfileSnapshot()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        profileSnapshotLiveData.observe { result ->
            result.doOnSuccess { setProfileSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        profileLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { profile ->
                account = profile
                bindProfile(profile)
            }
            result.doOnFailure { Timber.d(it.message) }
        }
        editAccountLiveEvent.observe { result ->
            result.doOnSuccess { setEditAccountTask(it) }
            result.doOnFailure { showErrorSnackbar("Не удалось обновить профиль") }
        }
        deleteAccountFirebaseLiveData.observe { result ->
            result.doOnSuccess { setDeleteAccountTask(it.task, it.uid) }
            result.doOnFailure { Timber.d(it.message) }
        }
        deleteAccountFireStoreLiveData.observe { result ->
            result.doOnSuccess { viewModel.openAuth() }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupToolbar()
        setupBirthday()
        setupSave()
    }

    /** Привязка профиля */
    private fun bindProfile(account: Account) = with(binding) {
        editTextNickname.setText(account.nickname)
        textViewBirthdayValue.text = account.birthday?.let { dateFormatter.formatStringFromLocalDate(it) }.orDefault()
        viewModel.birthday = account.birthday
        editTextSurname.setText(account.surname)
        editTextName.setText(account.name)
        editTextPatronymic.setText(account.patronymic)
    }

    private fun setupBirthday() = with(binding) {
        cardViewBirthday.setOnClickListener {
            val calendar = createDatePicker(viewModel.birthday)
            calendar.show(requireActivity().supportFragmentManager, resources.getString(R.string.calendar_account_tag))
            calendar.addOnPositiveButtonClickListener { millis ->
                viewModel.birthday = dateFormatter.getLocalDate(millis)
                textViewBirthdayValue.text = dateFormatter.getLocalDateString(millis)
                Timber.d("LOL TEXT VALUE: ${textViewBirthdayValue.text}")
            }
        }
    }

    /** Настройка тулбара */
    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuDelete) viewModel.deleteAccountFirebase()
            true
        }
    }

    /** Настройка кнопки Сохранить */
    private fun setupSave() = with(binding) {
        buttonSave.setOnClickListener { saveWithCheckValidate() }
    }

    /** Сохранение профиля с проверкой валидности данных */
    private fun saveWithCheckValidate() = with(binding) {
        if (textInputNickname.validate()) {
            try {
                val nickname = editTextNickname.text.toString()
                val surname = editTextSurname.text.toString()
                val name = editTextName.text.toString()
                val patronymic = editTextPatronymic.text.toString()

                val editedAccount = account?.copy(
                    nickname = nickname.orDefault(),
                    surname = surname.orDefault(),
                    name = name.orDefault(),
                    patronymic = patronymic.orDefault(),
                )

                if (editedAccount != null) {
                    if (viewModel.birthday != null) {
                        val birthday = viewModel.birthday
                        val editedAccountWithBirthday = editedAccount.copy(birthday = birthday)
                        viewModel.updateAccount(editedAccountWithBirthday)
                    } else {
                        viewModel.updateAccount(editedAccount)
                    }
                }
            } catch (e: Exception) {
                showErrorSnackbar(resources.getString(R.string.error_something_wrong_title))
            }
        }
    }

    private fun setProfileSnapshotTask(task: Task<DocumentSnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getProfile(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setEditAccountTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { showInfoSnackbar("Профиль обновлен") }
        addOnFailureListener { showErrorSnackbar("Не удалось обновить профиль") }
    }

    private fun setDeleteAccountTask(task: Task<Void>?, uid: String) {
        task?.apply {
            addOnSuccessListener {
                try {
                    notificationCore.cancelWorker(requireActivity().application, uid)
                } catch (e: Exception) {
                    Timber.d(e.message)
                }
                viewModel.deleteAccountFireStore()
            }
            addOnFailureListener { Timber.d(it.message) }
        } ?: Timber.d("Не удалось удалить аккаунт")
    }

    private fun createDatePicker(date: LocalDate?): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Выберите дату рождения")
            .apply {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = today
                calendar[Calendar.YEAR] = 1900
                val startDate = calendar.timeInMillis

                calendar.timeInMillis = today
                val endDate = calendar.timeInMillis

                val openDate = date?.let {
                    calendar.timeInMillis = today
                    calendar[Calendar.YEAR] = date.year
                    calendar[Calendar.MONTH] = date.month.value - 1
                    calendar[Calendar.DAY_OF_MONTH] = date.dayOfMonth
                    calendar.timeInMillis
                } ?: endDate

                val constraint: CalendarConstraints = CalendarConstraints.Builder()
                    .setOpenAt(openDate)
                    .setStart(startDate)
                    .setEnd(endDate)
                    .build()
                setCalendarConstraints(constraint)
            }
            .build()
    }
}