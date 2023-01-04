package app.vazovsky.healsted.presentation.settings.aboutus

import android.os.Bundle
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentSettingsAboutUsBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.getColorFromAttribute
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.R as MaterialR

@AndroidEntryPoint
class SettingsAboutUsFragment : BaseFragment(R.layout.fragment_settings_about_us) {

    private val binding by viewBinding(FragmentSettingsAboutUsBinding::bind)
    private val viewModel: SettingsAboutUsViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setClicks()
        setDeveloped()
    }

    private fun setDeveloped() = with(binding.textViewDeveloper) {
        text = buildString {
            append(resources.getString(R.string.settings_about_us_developed_prev))
            append(" ")
            append(resources.getString(R.string.settings_about_us_developed_link))
        }
        setOnClickListener { viewModel.openTelegramVazovsky() }
    }

    private fun setClicks() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
        textViewShare.setOnClickListener { viewModel.shareUrl() }
        textViewTelegram.setOnClickListener { viewModel.openTelegramChannel() }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}