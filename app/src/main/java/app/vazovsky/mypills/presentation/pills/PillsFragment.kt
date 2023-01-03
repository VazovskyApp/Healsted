package app.vazovsky.mypills.presentation.pills

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentPillsBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.pills.tab.active.adapter.ActivePillsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PillsFragment : BaseFragment(R.layout.fragment_pills) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillsBinding::bind)
    private val viewModel: PillsViewModel by viewModels()

    @Inject lateinit var activePillsAdapter: ActivePillsAdapter

    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupTabs()
        fabAddPill.setOnClickListener {
            viewModel.openAddPill()
        }
    }

    private fun setupTabs() = with(binding) {
        val pagerAdapter = PillsFragmentPagerAdapter(this@PillsFragment)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
        }.attach()
    }
}