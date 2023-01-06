package app.vazovsky.healsted.presentation.health

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.FragmentHealthBinding
import app.vazovsky.healsted.extensions.addDefaultGridSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.health.adapter.HealthAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** Экран мониторинга здоровья */
@AndroidEntryPoint
class HealthFragment : BaseFragment(R.layout.fragment_health) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentHealthBinding::bind)
    private val viewModel: HealthViewModel by viewModels()

    @Inject lateinit var healthAdapter: HealthAdapter

    override fun callOperations() {
        viewModel.getHealth()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewHealthMonitoring.adapter = healthAdapter
        recyclerViewHealthMonitoring.layoutManager = GridLayoutManager(context, 2)
        recyclerViewHealthMonitoring.addDefaultGridSpaceItemDecoration(
            spanCount = 2,
            spacing = R.dimen.padding_16,
            includeEdge = true,
            excludeTopEdge = false,
        )
        healthAdapter.onItemClick = { attr ->
            viewModel.openAttribute(attr)
        }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        healthLiveData.observe { result ->
            result.doOnSuccess { healthMonitoring ->
                bindHealthMonitoring(healthMonitoring)
            }
        }
    }

    private fun bindHealthMonitoring(healthMonitoring: List<MonitoringAttribute>) = with(binding) {
        healthAdapter.submitList(healthMonitoring)
    }
}