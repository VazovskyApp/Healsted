package app.vazovsky.mypills.presentation.pills.tab

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentCompletedPillsTabBinding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class CompletedPillsTab : BaseFragment(R.layout.fragment_completed_pills_tab) {

    private val binding by viewBinding(FragmentCompletedPillsTabBinding::bind)
    private val viewModel: CompletedPillsTabViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {

    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}