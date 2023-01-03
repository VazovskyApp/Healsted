package app.vazovsky.healsted.presentation.health.attribute

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentHealthAttributeBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthAttributeFragment : BaseFragment(R.layout.fragment_health_attribute) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val args: HealthAttributeFragmentArgs by navArgs()
    private val monitoring by lazy { args.healthMonitoring }

    private val binding by viewBinding(FragmentHealthAttributeBinding::bind)
    private val viewModel: HealthAttributeViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
        setupMonitoring()

    }

    private fun setupMonitoring() = with(binding) {
        toolbar.title = monitoring.type.toString()
        textViewValue.text = monitoring.value
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}