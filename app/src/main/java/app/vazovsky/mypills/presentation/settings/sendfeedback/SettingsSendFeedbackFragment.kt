package app.vazovsky.mypills.presentation.settings.sendfeedback

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentSettingsSendFeedbackBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
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
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}