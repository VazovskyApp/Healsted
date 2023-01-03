package app.vazovsky.mypills.presentation.pills

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.databinding.FragmentPillsBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.extensions.observeNavigationCommands
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.pills.tab.active.adapter.ActivePillsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class PillsFragment : BaseFragment(R.layout.fragment_pills) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentPillsBinding::bind)
    private val viewModel: PillsViewModel by viewModels()

    @Inject lateinit var activePillsAdapter: ActivePillsAdapter

    override fun callOperations() {
        viewModel.getPills()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupRecyclerView()
        fabAddPill.setOnClickListener {
            viewModel.openAddPill()
        }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        pillsLiveDataLiveData.observe { result ->
            result.doOnSuccess { pills ->
                bindPills(pills)
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewActivePills.adapter = activePillsAdapter
        recyclerViewActivePills.layoutManager = GridLayoutManager(requireContext(), 2)
        activePillsAdapter.onItemClick = {
            Timber.d("LOL ${it.name}")
        }
    }

    private fun bindPills(pills: List<Pill>) {
        activePillsAdapter.submitList(pills)
    }
}