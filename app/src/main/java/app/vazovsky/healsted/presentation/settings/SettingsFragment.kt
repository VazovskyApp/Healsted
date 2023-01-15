package app.vazovsky.healsted.presentation.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.data.model.SettingType
import app.vazovsky.healsted.data.model.SettingsItem
import app.vazovsky.healsted.databinding.FragmentSettingsBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.settings.adapter.SettingsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран настроек */
@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

    @Inject lateinit var notificationCore: NotificationCore
    @Inject lateinit var settingsAdapter: SettingsAdapter

    override fun callOperations() {
        viewModel.getSettings()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        settingsLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { settings -> bindSettings(settings) }
            result.doOnFailure { Timber.d(it.message) }
        }
        signOutLiveEvent.observe { result ->
            result.doOnSuccess {
                notificationCore.cancelWorker(requireActivity().application, it.uid)
                viewModel.openAuth()
            }
            result.doOnFailure { showErrorSnackbar(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getSettings() }

        setupRecyclerView()
    }

    private fun bindSettings(settings: List<SettingsItem>) {
        settingsAdapter.submitList(settings)
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewSettings.adapter = settingsAdapter
        settingsAdapter.onItemClick = { item ->
            when (item.type) {
                SettingType.ACCOUNT -> viewModel.openAccount()
                SettingType.NOTIFICATION -> viewModel.openNotifications()
                SettingType.REPORT_A_BAG -> viewModel.openReportBug()
                SettingType.SEND_FEEDBACK -> viewModel.openSendFeedback()
                SettingType.FEATURES -> viewModel.openFeatures()
                SettingType.ABOUT_US -> viewModel.openAboutUs()
                SettingType.LOG_OUT -> {
                    viewModel.signOut()
                }
            }
        }
    }
}