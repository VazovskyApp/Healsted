package app.vazovsky.mypills.presentation.addpill

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentAddPillBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class AddPillFragment : BaseFragment(R.layout.fragment_add_pill) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentAddPillBinding::bind)
    private val viewModel: AddPillViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}