package app.vazovsky.mypills.presentation.routing

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentRoutingBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.extensions.observeNavigationCommands
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingFragment : BaseFragment(R.layout.fragment_routing) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentRoutingBinding::bind)
    private val viewModel: RoutingViewModel by viewModels()

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