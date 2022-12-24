package app.vazovsky.mypills.presentation.health

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.databinding.FragmentHealthBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.health.monitoring.HealthAdapter
import app.vazovsky.mypills.presentation.view.GridSpaceItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HealthFragment : BaseFragment(R.layout.fragment_health) {
    override val showBottomNavigationView: Boolean
        get() = true

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
        recyclerViewHealthMonitoring.addItemDecoration(GridSpaceItemDecoration(2, 20, false, false))
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