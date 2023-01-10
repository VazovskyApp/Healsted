package app.vazovsky.healsted.presentation.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.databinding.FragmentDashboardBinding
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.dashboard.adapter.TodayPillsAdapter
import app.vazovsky.healsted.presentation.view.StateViewFlipper
import app.vazovsky.healsted.presentation.view.timeline.OnDateSelectedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import timber.log.Timber

/** Экран дашборда */
@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    @Inject lateinit var dateFormatter: DateFormatter
    @Inject lateinit var todayPillsAdapter: TodayPillsAdapter

    override fun callOperations() {
        viewModel.getProfileSnapshot()
        viewModel.getTodayPillsSnapshot(LocalDate.now().toStartOfDayTimestamp())
        viewModel.getTodayMoodSnapshot()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        profileSnapshotLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener { snapshot ->
                    viewModel.getProfile(snapshot)
                }
            }
        }
        profileLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { account ->
                bindToolbar(account)
            }
        }
        todayPillsSnapshotLiveData.observe { result ->
            result.doOnSuccess { getTodayPillsResult ->
                getTodayPillsResult.snapshotTask.addOnSuccessListener { snapshot ->
                    viewModel.getTodayPills(snapshot, getTodayPillsResult.date)
                }
            }
        }
        todayPillsLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { pills ->
                todayPillsAdapter.submitList(pills)
            }
        }
        todayMoodSnapshotLiveData.observe { result ->
            binding.stateViewFlipperMood.changeState(StateViewFlipper.State.LOADING)
            result.doOnSuccess { task ->
                setMoodSnapshotTask(task)
            }
        }
        todayMoodLiveData.observe { result ->
            binding.stateViewFlipperMood.setStateFromResult(result)
            result.doOnSuccess { mood ->
                bindMood(mood)
            }
        }
        updateMoodLiveEvent.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    Timber.d("Настроение обновлено")
                }
                task.addOnFailureListener {
                    Timber.d(it.localizedMessage)
                }
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipperMood.setRetryMethod { viewModel.getTodayPillsSnapshot(LocalDate.now().toStartOfDayTimestamp()) }
        stateViewFlipper.setRetryMethod { viewModel.getTodayPillsSnapshot(viewModel.selectedDate) }

        setupTimeline()
        setupRecyclerView()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        constraintLayout.updatePadding(
            bottom = bottomNavigationViewHeight + resources.getDimensionPixelOffset(R.dimen.margin_20)
        )
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateMood(Mood(binding.ratingBarMood.getCurrentRateStatus()))
    }

    private fun setMoodSnapshotTask(task: Task<DocumentSnapshot>) {
        task.apply {
            addOnSuccessListener { snapshot ->
                viewModel.getTodayMood(snapshot)
            }
            addOnFailureListener {
                Timber.d(it.localizedMessage)
            }
        }
    }

    private fun bindToolbar(profile: Account) = with(binding.toolbar) {
        val currentHour = LocalTime.now().hour
        title = resources.getString(
            when (currentHour) {
                in 6..11 -> R.string.welcome_string_morning
                in 12..16 -> R.string.welcome_string_afternoon
                in 17..21 -> R.string.welcome_string_evening
                else -> R.string.welcome_string_night
            },
            profile.nickname,
        )
    }

    private fun bindMood(mood: Mood) = with(binding) {
        ratingBarMood.setCurrentRateStatus(mood.value)
    }

    private fun setupTimeline() = with(binding) {
        datePickerTimeline.setInitialDate(2023, 0, 1)
        datePickerTimeline.setActiveDate(Calendar.getInstance())
        textViewTitle.text = dateFormatter.getDisplayDifferentDates(LocalDate.now(), LocalDate.now())
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                val selectedDate = LocalDate.of(year, month + 1, day)
                textViewTitle.text = dateFormatter.getDisplayDifferentDates(LocalDate.now(), selectedDate)
                textViewDate.text = dateFormatter.formatDateWithDayOfWeek(selectedDate)
                viewModel.selectedDate = selectedDate.toStartOfDayTimestamp()
                viewModel.getTodayPillsSnapshot(viewModel.selectedDate)
            }

            override fun onDisabledDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int, isDisabled: Boolean) = Unit
        })
    }

    private fun setupRecyclerView() = with(binding.recyclerViewTodayPills) {
        adapter = todayPillsAdapter.apply {
            onItemClick = { viewModel.openEditPill(it) }
        }
        emptyView = binding.emptyViewTodayPills
        addLinearSpaceItemDecoration(R.dimen.padding_8)
    }
}