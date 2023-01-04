package app.vazovsky.healsted.presentation.dashboard

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentDashboardBinding
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.dashboard.adapter.TodayPillsAdapter
import app.vazovsky.healsted.presentation.view.timeline.OnDateSelectedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    @Inject lateinit var todayPillsAdapter: TodayPillsAdapter

    override fun callOperations() {
        viewModel.getTodayPills()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        todayPillsLiveData.observe { result ->
            result.doOnSuccess { pills ->
                Timber.d("LOL $pills")
                todayPillsAdapter.submitList(pills)
            }
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutContent.updatePadding(bottom = bottomNavigationViewHeight)
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupTimeline()
        setupRecyclerView()
    }

    private fun setupTimeline() = with(binding) {
        datePickerTimeline.setInitialDate(2023, 0, 1)
        datePickerTimeline.setActiveDate(Calendar.getInstance())
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {

            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                textViewTitle.text = when (LocalDate.of(year, month + 1, day)) {
                    LocalDate.now() -> "Сегодня"
                    in LocalDate.MIN..LocalDate.now() -> "Ранее"
                    in LocalDate.now()..LocalDate.MAX -> "Позже"
                    else -> "Странно"
                }
                textViewDate.text = "$day $month, $dayOfWeek"
            }

            override fun onDisabledDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int, isDisabled: Boolean) {

            }
        })
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewTodayPills.adapter = todayPillsAdapter.apply {
            onItemClick = { pill -> viewModel.openEditPill(pill) }
        }
        recyclerViewTodayPills.addLinearSpaceItemDecoration(R.dimen.padding_8)
    }
}