package app.vazovsky.mypills.presentation.pills.tab

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentActivePillsTabBinding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class ActivePillsTab : BaseFragment(R.layout.fragment_active_pills_tab) {

    private val binding by viewBinding(FragmentActivePillsTabBinding::bind)
    private val viewModel: ActivePillsTabViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {

    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}