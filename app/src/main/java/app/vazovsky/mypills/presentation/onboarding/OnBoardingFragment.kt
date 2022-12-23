package app.vazovsky.mypills.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentDashboardBinding
import app.vazovsky.mypills.databinding.FragmentOnboardingBinding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.dashboard.DashboardViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() {

    }
}