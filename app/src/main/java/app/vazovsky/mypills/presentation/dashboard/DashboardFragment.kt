package app.vazovsky.mypills.presentation.dashboard

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentDashboardBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.view.timeline.OnDateSelectedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutContent.updatePadding(bottom = bottomNavigationViewHeight)
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupTimeline()
    }

    private fun setupTimeline() = with(binding) {
        datePickerTimeline.setInitialDate(2023, 0, 1)
        datePickerTimeline.setActiveDate(Calendar.getInstance())
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            //TODO поправить
            @RequiresApi(Build.VERSION_CODES.O)
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
}