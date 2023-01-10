package app.vazovsky.healsted.presentation.settings.account

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentSettingsAccountBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.showInfoSnackbar
import app.vazovsky.healsted.extensions.toOffsetDateTime
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
            result.doOnSuccess { task ->
                task.addOnSuccessListener { snapshot ->
                    viewModel.getProfile(snapshot)
                }
            }
        }
        profileLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { profile ->
                account = profile
                bindProfile(profile)
            }
        }
        editAccountLiveEvent.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    showInfoSnackbar("Профиль обновлен")
                }
                task.addOnFailureListener {
                    showErrorSnackbar("Не удалось обновить профиль")
                }
            }
            result.doOnFailure {
                showErrorSnackbar("Не удалось обновить профиль")
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }

        setupConfirm()
    }

    private fun bindProfile(account: Account) = with(binding) {
        editTextNickname.setText(account.nickname)
        editTextBirthday.setText(dateFormatter.formatStandardDateFull(account.birthday.orDefault().toOffsetDateTime()))
    }

    private fun setupConfirm() = with(binding) {
        buttonSave.setOnClickListener {
            val nickname = editTextNickname.text.toString()
            val birthday = dateFormatter.parseDateFromString(editTextBirthday.text.toString())

            if (checkInputs()) {
                val editedAccount = account?.copy(
                    nickname = nickname,
                    birthday = birthday,
                )
                if (editedAccount != null) {
                    viewModel.updateAccount(editedAccount)
                }
            }
        }
    }

    private fun checkInputs(): Boolean = with(binding) {
        var validated = true
        listOf(textInputNickname, textInputBirthday).forEach {
            validated = validated.and(it.validate())
        }
        return@with validated
    }
}