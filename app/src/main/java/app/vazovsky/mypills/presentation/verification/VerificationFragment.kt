package app.vazovsky.mypills.presentation.verification

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentVerificationBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

private const val STATE_LOADING = 0
private const val STATE_DATA = 1

private const val DEFAULT_CODE_LENGTH = 4

private const val SECONDS_IN_MINUTE = 60
private const val MILLIS_IN_SECOND = 1000L
private const val COUNT_DOWN_INTERVAL = 1000L
private const val VISIBLE_ERROR_PIN_CODE_DURATION = 300L

@AndroidEntryPoint
class VerificationFragment : BaseFragment(R.layout.fragment_verification) {

    private val binding by viewBinding(FragmentVerificationBinding::bind)
    private val viewModel: VerificationViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}