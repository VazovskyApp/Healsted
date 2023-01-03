package app.vazovsky.healsted.presentation.settings.sendfeedback

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentSettingsSendFeedbackBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsSendFeedbackFragment : BaseFragment(R.layout.fragment_settings_send_feedback) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentSettingsSendFeedbackBinding::bind)
    private val viewModel: SettingsSendFeedbackViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupToolbar()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
        toolbar.menu.findItem(R.id.menuSend).isEnabled = !editTextFeedback.text.isNullOrBlank()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}