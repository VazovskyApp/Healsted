package app.vazovsky.mypills.presentation.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.SettingType
import app.vazovsky.mypills.data.model.SettingsItem
import app.vazovsky.mypills.databinding.FragmentSettingsBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.settings.adapter.SettingsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

    @Inject lateinit var settingsAdapter: SettingsAdapter

    override fun callOperations() {
        viewModel.getSettings()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        settingsLiveData.observe { result ->
            result.doOnSuccess { settings ->
                bindSettings(settings)
            }
        }
    }

    private fun bindSettings(settings: List<SettingsItem>) {
        settingsAdapter.submitList(settings)
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewSettings.adapter = settingsAdapter
        settingsAdapter.onItemClick = { item ->
            when (item.type) {
                SettingType.ACCOUNT -> viewModel.openAccount()
                SettingType.NOTIFICATION -> viewModel.openNotifications()
                SettingType.REPORT_A_BAG -> viewModel.openReportBug()
                SettingType.SEND_FEEDBACK -> viewModel.openSendFeedback()
                SettingType.ABOUT_US -> viewModel.openAboutUs()
                SettingType.LOG_OUT -> {
                    /** TODO сделать выход из профиля реально / почему-то после этого settings это dashboard */
                    viewModel.openAuth()
                }
            }
        }
    }
}