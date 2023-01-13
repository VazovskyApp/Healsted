package app.vazovsky.healsted.presentation.health.attribute

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.databinding.FragmentHealthAttributeBinding
import app.vazovsky.healsted.extensions.addVerticalDividerItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.health.REQUEST_KEY_UPDATE_HEALTH
import app.vazovsky.healsted.presentation.health.attribute.adapter.HealthAttributeHistoryAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject
import timber.log.Timber

/** Экран с информацией об одном из атрибутов здоровья */
@AndroidEntryPoint
class HealthAttributeFragment : BaseFragment(R.layout.fragment_health_attribute) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentHealthAttributeBinding::bind)
    private val viewModel: HealthAttributeViewModel by viewModels()

    private val args: HealthAttributeFragmentArgs by navArgs()
    private val type by lazy { args.healthMonitoring.type }

    @Inject lateinit var healthAttributeHistoryAdapter: HealthAttributeHistoryAdapter

    override fun callOperations() {
        viewModel.getMonitoringSnapshot(type)
        viewModel.getHistorySnapshot(type)
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        monitoringSnapshotLiveData.observe { result ->
            result.doOnSuccess { setMonitoringSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        monitoringLiveData.observe { result ->
            result.doOnSuccess { monitoring -> bindMonitoring(monitoring) }
            result.doOnFailure { Timber.d(it.message) }
        }
        historySnapshotLiveData.observe { result ->
            result.doOnSuccess { setHistorySnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        historyLiveData.observe { result ->
            result.doOnSuccess { history -> bindHistory(history) }
            result.doOnFailure { Timber.d(it.message) }
        }
        updateMonitoringLiveEvent.observe { result ->
            result.doOnSuccess { setUpdateMonitoringTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    private fun setUpdateMonitoringTask(task: Task<Void>) = with(task) {
        addOnSuccessListener {
            viewModel.getMonitoringSnapshot(type)
            viewModel.getHistorySnapshot(type)
        }
        addOnFailureListener { Timber.d(it.message) }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupToolbar()
        setupRecyclerView()
        setupUpdate()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutEditValue.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        title = type.toString()
    }

    /** Настройка RecyclerView */
    private fun setupRecyclerView() = with(binding) {
        recyclerViewHistory.adapter = healthAttributeHistoryAdapter.apply {
            onItemClick = {}
        }
        recyclerViewHistory.addVerticalDividerItemDecoration()
        recyclerViewHistory.emptyView = emptyViewHistory
    }

    private fun setupUpdate() = with(binding) {
        buttonUpdate.setOnClickListener {
            setFragmentResult(REQUEST_KEY_UPDATE_HEALTH, bundleOf())
            val newValue = when (type) {
                MonitoringType.BLOOD_PRESSURE -> buildString {
                    append(editTextNewValue.text)
                    append("/")
                    append(editTextNewValueSecond.text)
                }

                else -> editTextNewValue.text.toString()
            }

            viewModel.updateMonitoring(MonitoringAttribute(value = newValue, type, LocalDate.now()))
        }
    }

    private fun setMonitoringSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getMonitoring(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setHistorySnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getHistory(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    /** Привязка истории */
    private fun bindHistory(history: List<MonitoringAttribute>) {
        healthAttributeHistoryAdapter.submitList(history)
    }

    /** Привязка информации об атрибуте мониторинга */
    private fun bindMonitoring(monitoring: MonitoringAttribute) = with(binding) {
        textViewCurrentValue.text = buildString {
            append(resources.getString(R.string.health_attribute_current_value))
            append(" ")
            append(monitoring.value)
        }
        bindNewValueHint(monitoring)
    }

    /** Привязка подсказок */
    private fun bindNewValueHint(monitoring: MonitoringAttribute) = with(binding) {
        textInputNewValueSecond.isVisible = monitoring.type == MonitoringType.BLOOD_PRESSURE
        when (monitoring.type) {
            MonitoringType.BLOOD_PRESSURE -> {
                textInputNewValue.hint = "Верхнее"
                textInputNewValueSecond.hint = "Нижнее"
            }

            else -> setNewValueHintExceptBloodPressure(monitoring.type)
        }
    }

    private fun setNewValueHintExceptBloodPressure(type: MonitoringType) = with(binding) {
        textInputNewValue.hint = type.toString()
        textInputNewValueSecond.hint = ""
    }
}
