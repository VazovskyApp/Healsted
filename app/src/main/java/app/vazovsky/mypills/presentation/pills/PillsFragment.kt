package app.vazovsky.mypills.presentation.pills

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentPillsBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.extensions.observeNavigationCommands
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PillsFragment : BaseFragment(R.layout.fragment_pills) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentPillsBinding::bind)
    private val viewModel: PillsViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}