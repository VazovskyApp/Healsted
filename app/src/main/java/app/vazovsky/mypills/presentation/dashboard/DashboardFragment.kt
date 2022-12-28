package app.vazovsky.mypills.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentDashboardBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        buttonAddPill.setOnClickListener {
            viewModel.openAddPill()
        }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}