package app.vazovsky.healsted.presentation.settings.account

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentSettingsAccountBinding
import app.vazovsky.healsted.extensions.checkInputs
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.showInfoSnackbar
import app.vazovsky.healsted.extensions.toOffsetDateTime
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран с редактированием профиля */
@AndroidEntryPoint
class SettingsAccountFragment : BaseFragment(R.layout.fragment_settings_account) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentSettingsAccountBinding::bind)
    private val viewModel: SettingsAccountViewModel by viewModels()

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
            result.doOnSuccess { viewModel.deleteAccountFireStore() }
            result.doOnFailure { Timber.d(it.message) }
        }
        deleteAccountFireStoreLiveData.observe { result ->
            result.doOnSuccess { viewModel.openAuth() }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }

        setupConfirm()
        setupDelete()
    }

    private fun bindProfile(account: Account) = with(binding) {
        editTextNickname.setText(account.nickname)
        editTextBirthday.setText(dateFormatter.formatStandardDateFull(account.birthday.orDefault().toOffsetDateTime()))
        editTextSurname.setText(account.surname)
        editTextName.setText(account.name)
        editTextPatronymic.setText(account.patronymic)
    }

    private fun setupConfirm() = with(binding) {
        buttonSave.setOnClickListener {
            val nickname = editTextNickname.text.toString()
            val surname = editTextSurname.text.toString()
            val name = editTextName.text.toString()
            val patronymic = editTextPatronymic.text.toString()
            val birthday = dateFormatter.parseDateFromString(editTextBirthday.text.toString())

            val isValidate = listOf(textInputNickname, textInputBirthday).checkInputs()
            if (isValidate) {
                val editedAccount = account?.copy(
                    nickname = nickname,
                    surname = surname,
                    name = name,
                    patronymic = patronymic,
                    birthday = birthday,
                )
                if (editedAccount != null) {
                    viewModel.updateAccount(editedAccount)
                }
            }
        }
    }

    private fun setupDelete() = with(binding) {
        buttonDelete.setOnClickListener {
            viewModel.deleteAccountFirebase()
        }
    }

    private fun setEditAccountTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { showInfoSnackbar("Профиль обновлен") }
        addOnFailureListener { showErrorSnackbar("Не удалось обновить профиль") }
    }

    private fun setProfileSnapshotTask(task: Task<DocumentSnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getProfile(it) }
        addOnFailureListener { Timber.d(it.message) }
    }
}