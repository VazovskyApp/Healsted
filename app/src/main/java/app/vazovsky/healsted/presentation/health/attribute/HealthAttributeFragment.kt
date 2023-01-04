package app.vazovsky.healsted.presentation.health.attribute

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.FragmentHealthAttributeBinding
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.health.attribute.adapter.HealthAttributeHistoryAdapter
import app.vazovsky.healsted.presentation.view.LinearSpaceItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HealthAttributeFragment : BaseFragment(R.layout.fragment_health_attribute) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val args: HealthAttributeFragmentArgs by navArgs()
    private val monitoring by lazy { args.healthMonitoring }

    private val binding by viewBinding(FragmentHealthAttributeBinding::bind)
    private val viewModel: HealthAttributeViewModel by viewModels()
    @Inject lateinit var healthAttributeHistoryAdapter: HealthAttributeHistoryAdapter

    override fun callOperations() {
        viewModel.getHistory(monitoring.type)
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        historyLiveData.observe { result ->
            result.doOnSuccess { history ->
                bindHistory(history)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
        setupMonitoring()
        setupRecyclerView()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        constraintLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun bindHistory(history: List<MonitoringAttribute>) {
        healthAttributeHistoryAdapter.submitList(history)
    }

    private fun setupMonitoring() = with(binding) {
        toolbar.title = monitoring.type.toString()
        textViewCurrentValue.text = buildString {
            append(resources.getString(R.string.health_attribute_current_value))
            append(" ")
            append(monitoring.value)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewHistory.adapter = healthAttributeHistoryAdapter
        recyclerViewHistory.addLinearSpaceItemDecoration(R.dimen.padding_16)
    }
}