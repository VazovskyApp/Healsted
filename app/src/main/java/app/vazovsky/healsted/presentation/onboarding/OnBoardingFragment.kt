package app.vazovsky.healsted.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentOnboardingBinding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/** Экран онбординга с описанием функций приложения */
@AndroidEntryPoint
class OnBoardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {

    }
}