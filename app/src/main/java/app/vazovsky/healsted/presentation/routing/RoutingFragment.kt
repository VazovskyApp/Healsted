package app.vazovsky.healsted.presentation.routing

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentRoutingBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingFragment : BaseFragment(R.layout.fragment_routing) {

    override val showBottomNavigationView = true

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