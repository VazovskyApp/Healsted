package app.vazovsky.mypills.presentation.verification

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentVerificationBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class VerificationFragment : BaseFragment(R.layout.fragment_verification) {

    private val binding by viewBinding(FragmentVerificationBinding::bind)
    private val viewModel: VerificationViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        buttonDashboard.setOnClickListener {
            viewModel.openDashboard()
        }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}