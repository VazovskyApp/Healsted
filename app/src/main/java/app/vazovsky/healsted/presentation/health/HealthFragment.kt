package app.vazovsky.healsted.presentation.health

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.FragmentHealthBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/** Экран мониторинга здоровья */
@AndroidEntryPoint
class HealthFragment : BaseFragment(R.layout.fragment_health) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentHealthBinding::bind)
    private val viewModel: HealthViewModel by viewModels()

    override fun callOperations() {
        viewModel.getWeightSnapshot()
        viewModel.getHeightSnapshot()
        viewModel.getTemperatureSnapshot()
        viewModel.getBloodPressureSnapshot()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        weightSnapshotLiveData.observe { result ->
            result.doOnSuccess { setWeightSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        weightLiveData.observe { result ->
            binding.viewWeight.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { weight -> bindWeight(weight) }
            result.doOnFailure { Timber.d(it.message) }
        }
        heightSnapshotLiveData.observe { result ->
            result.doOnSuccess { setHeightSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        heightLiveData.observe { result ->
            binding.viewHeight.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { height -> bindHeight(height) }
            result.doOnFailure { Timber.d(it.message) }
        }
        temperatureSnapshotLiveData.observe { result ->
            result.doOnSuccess { setTemperatureSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        temperatureLiveData.observe { result ->
            binding.viewTemperature.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { temperature -> bindTemperature(temperature) }
            result.doOnFailure { Timber.d(it.message) }
        }
        bloodPressureSnapshotLiveData.observe { result ->
            result.doOnSuccess { setBloodPressureSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        bloodPressureLiveData.observe { result ->
            binding.viewBloodPressure.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { bloodPressure -> bindBloodPressure(bloodPressure) }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        binding.root.fitTopInsetsWithPadding()

        setupCards()
    }

    /** Настройка карточек */
    private fun setupCards() = with(binding) {
        viewWeight.apply {
            stateViewFlipper.setRetryMethod { viewModel.getWeightSnapshot() }
            cardView.setCardBackgroundColor(requireContext().getColor(R.color.pillsCardBlue))
        }
        viewHeight.apply {
            stateViewFlipper.setRetryMethod { viewModel.getHeightSnapshot() }
            cardView.setCardBackgroundColor(requireContext().getColor(R.color.pillsCardGreen))
        }
        viewTemperature.apply {
            stateViewFlipper.setRetryMethod { viewModel.getTemperatureSnapshot() }
            cardView.setCardBackgroundColor(requireContext().getColor(R.color.pillsCardOrange))
        }
        viewBloodPressure.apply {
            stateViewFlipper.setRetryMethod { viewModel.getBloodPressureSnapshot() }
            cardView.setCardBackgroundColor(requireContext().getColor(R.color.pillsCardRed))
        }
    }

    /** Привязка веса */
    private fun bindWeight(weight: MonitoringAttribute) = with(binding.viewWeight) {
        textViewTitle.text = weight.type.toString()
        textViewValue.text = weight.value
    }

    /** Привязка роста */
    private fun bindHeight(height: MonitoringAttribute) = with(binding.viewHeight) {
        textViewTitle.text = height.type.toString()
        textViewValue.text = height.value
    }

    /** Привязка температуры */
    private fun bindTemperature(temperature: MonitoringAttribute) = with(binding.viewTemperature) {
        textViewTitle.text = temperature.type.toString()
        textViewValue.text = temperature.value
    }

    /** Привязка давления */
    private fun bindBloodPressure(bloodPressure: MonitoringAttribute) = with(binding.viewBloodPressure) {
        textViewTitle.text = bloodPressure.type.toString()
        textViewValue.text = bloodPressure.value
    }

    private fun setWeightSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getWeight(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setHeightSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getHeight(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setTemperatureSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getTemperature(it) }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setBloodPressureSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getBloodPressure(it) }
        addOnFailureListener { Timber.d(it.message) }
    }
}
