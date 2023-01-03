package app.vazovsky.healsted.presentation.pills

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentPillsBinding
import app.vazovsky.healsted.extensions.addDefaultGridSpaceItemDecoration
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.pills.adapter.PillsAdapter
import app.vazovsky.healsted.presentation.pills.tab.PillsTabsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PillsFragment : BaseFragment(R.layout.fragment_pills) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillsBinding::bind)
    private val viewModel: PillsViewModel by viewModels()

    @Inject lateinit var pillsTabsAdapter: PillsTabsAdapter
    @Inject lateinit var pillsAdapter: PillsAdapter

    override fun callOperations() {
        viewModel.getTabs()
        viewModel.getPills()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        tabsLiveData.observe { result ->
            result.doOnSuccess { tabs ->
                pillsTabsAdapter.submitList(tabs)
            }
        }
        pillsLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { pills ->
                pillsAdapter.submitList(pills)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupTabs()
        setupPills()
        fabAddPill.setOnClickListener {
            viewModel.openAddPill()
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        constraintLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun setupTabs() = with(binding) {
        recyclerViewTabs.adapter = pillsTabsAdapter.apply {
            onItemClick = {
                viewModel.onTabClick(it)
            }
        }
        recyclerViewTabs.addLinearSpaceItemDecoration(R.dimen.tabsSpace)

    }

    private fun setupPills() = with(binding) {
        recyclerViewPills.adapter = pillsAdapter.apply {
            onItemClick = {}
        }
        recyclerViewPills.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewPills.addDefaultGridSpaceItemDecoration(
            spanCount = 2,
            spacing = R.dimen.padding_16,
            includeEdge = true,
            excludeTopEdge = false,
        )
    }
}