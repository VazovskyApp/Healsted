package app.vazovsky.mypills.presentation.profile.routing

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentProfileRoutingBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileRoutingFragment : BaseFragment(R.layout.fragment_profile_routing) {

    private val binding by viewBinding(FragmentProfileRoutingBinding::bind)
    private val viewModel: ProfileRoutingViewModel by viewModels()

    override fun callOperations() {
        viewModel.runIntroFlow()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        initLiveEvent.observe { result ->
            result.doOnSuccess { navigateToResult(it) }
        }
    }
}