package app.vazovsky.healsted.presentation.settings.aboutus

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentSettingsAboutUsBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/** Экран с информацией о приложении */
@AndroidEntryPoint
class SettingsAboutUsFragment : BaseFragment(R.layout.fragment_settings_about_us) {

    private val binding by viewBinding(FragmentSettingsAboutUsBinding::bind)
    private val viewModel: SettingsAboutUsViewModel by viewModels()

    override fun callOperations() {
        viewModel.getVersionName()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getVersionName() }

        setClicks()
        setDeveloped()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        appVersionLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { version -> bindVersion(version) }
            result.doOnFailure { }
        }
    }

    /** Настройка всех кликов */
    private fun setClicks() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
        textViewShare.setOnClickListener { viewModel.shareUrl() }
        textViewTelegram.setOnClickListener { viewModel.openTelegramChannel() }
    }

    private fun bindVersion(version: String) = with(binding.textViewVersion) {
        text = buildString {
            append(getString(R.string.settings_about_us_version))
            append(" ")
            append(version)
        }
    }

    /** Настройка кнопки Developed by VazovskyApp */
    private fun setDeveloped() = with(binding.textViewDeveloper) {
        text = resources.getString(R.string.settings_about_us_developed)
        setOnClickListener { viewModel.openTelegramVazovsky() }
    }
}