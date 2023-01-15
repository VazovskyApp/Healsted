package app.vazovsky.healsted.presentation.settings.notification

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentSettingsNotificationBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsNotificationFragment : BaseFragment(R.layout.fragment_settings_notification) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentSettingsNotificationBinding::bind)
    private val viewModel: SettingsNotificationViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}