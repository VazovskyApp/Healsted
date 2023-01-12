package app.vazovsky.healsted.presentation.pills

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentPillsBinding
import app.vazovsky.healsted.domain.pills.GetPillsUseCase
import app.vazovsky.healsted.extensions.addDefaultGridSpaceItemDecoration
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.pills.adapter.PillsAdapter
import app.vazovsky.healsted.presentation.pills.tab.PillsTabsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

const val REQUEST_KEY_UPDATE_PILLS = "request_key_update_pills"

/** Экран со списками всех лекарств */
@AndroidEntryPoint
class PillsFragment : BaseFragment(R.layout.fragment_pills) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillsBinding::bind)
    private val viewModel: PillsViewModel by viewModels()

    @Inject lateinit var pillsTabsAdapter: PillsTabsAdapter
    @Inject lateinit var pillsAdapter: PillsAdapter

    override fun callOperations() {
        viewModel.getTabs()
        viewModel.getPillsSnapshot()
        viewModel.getLocalPills()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        tabsLiveData.observe { result ->
            result.doOnSuccess { tabs -> pillsTabsAdapter.submitList(tabs) }
            result.doOnFailure { Timber.d(it.message) }
        }
        pillsSnapshotLiveData.observe { result ->
            result.doOnSuccess { pillsResult -> setPillsSnapshotTask(pillsResult) }
            result.doOnFailure { Timber.d(it.message) }
        }
        pillsLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { pills -> pillsAdapter.submitList(pills) }
            result.doOnFailure { Timber.d(it.message) }
        }
        localPillsLiveEvent.observe { result ->
            result.doOnSuccess { Timber.d("LOL result: $it") }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    private fun setPillsSnapshotTask(pillsResult: GetPillsUseCase.Result) = with(pillsResult.snapshotTask) {
        addOnSuccessListener { snapshot -> viewModel.getPills(snapshot, pillsResult.slot) }
        addOnFailureListener { Timber.d(it.message) }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setFragmentResultListener(REQUEST_KEY_UPDATE_PILLS) { _, _ ->
            viewModel.getPillsSnapshot(viewModel.selectedPillTab)
        }

        setupTabs()
        setupPills()
        fabAddPill.setOnClickListener {
            viewModel.openAddPill()
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun setupTabs() = with(binding) {
        recyclerViewTabs.adapter = pillsTabsAdapter.apply {
            onItemClick = {
                viewModel.selectedPillTab = it.slot
                viewModel.onTabClick(it)
            }
        }
        recyclerViewTabs.addLinearSpaceItemDecoration(R.dimen.tabsSpace)
    }

    private fun setupPills() = with(binding.recyclerViewPills) {
        adapter = pillsAdapter.apply {
            onItemClick = { pill ->
                viewModel.openEditPill(pill)
            }
        }
        emptyView = binding.emptyViewPills
        layoutManager = GridLayoutManager(requireContext(), 2)
        addDefaultGridSpaceItemDecoration(
            spanCount = 2,
            spacing = R.dimen.padding_16,
            includeEdge = true,
            excludeTopEdge = false,
        )
    }
}