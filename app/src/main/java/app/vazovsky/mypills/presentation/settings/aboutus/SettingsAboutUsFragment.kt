package app.vazovsky.mypills.presentation.settings.aboutus

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentSettingsAboutUsBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsAboutUsFragment : BaseFragment(R.layout.fragment_settings_about_us) {

    private val binding by viewBinding(FragmentSettingsAboutUsBinding::bind)
    private val viewModel: SettingsAboutUsViewModel by viewModels()

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