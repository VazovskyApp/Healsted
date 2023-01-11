package app.vazovsky.healsted.presentation.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.health.GetHealthMonitoringUseCase
import app.vazovsky.healsted.domain.health.ParseSnapshotToMonitoringUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val destinations: HealthDestinations,
    private val getHealthMonitoringUseCase: GetHealthMonitoringUseCase,
    private val parseSnapshotToMonitoringUseCase: ParseSnapshotToMonitoringUseCase,
) : BaseViewModel() {

    /** Информация о весе в виде QuerySnapshot */
    private val _weightSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val weightSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _weightSnapshotLiveData

    /** Информация о весе из QuerySnapshot */
    private val _weightLiveData = MutableLiveData<LoadableResult<MonitoringAttribute>>()
    val weightLiveData: LiveData<LoadableResult<MonitoringAttribute>> = _weightLiveData

    /** Информация о росте в виде QuerySnapshot */
    private val _heightSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val heightSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _heightSnapshotLiveData

    /** Информация о росте из QuerySnapshot */
    private val _heightLiveData = MutableLiveData<LoadableResult<MonitoringAttribute>>()
    val heightLiveData: LiveData<LoadableResult<MonitoringAttribute>> = _heightLiveData

    /** Информация о температуре в виде QuerySnapshot */
    private val _temperatureSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val temperatureSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _temperatureSnapshotLiveData

    /** Информация о температуре из QuerySnapshot */
    private val _temperatureLiveData = MutableLiveData<LoadableResult<MonitoringAttribute>>()
    val temperatureLiveData: LiveData<LoadableResult<MonitoringAttribute>> = _temperatureLiveData

    /** Информация о давлении в виде QuerySnapshot */
    private val _bloodPressureSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val bloodPressureSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _bloodPressureSnapshotLiveData

    /** Информация о давлении из QuerySnapshot */
    private val _bloodPressureLiveData = MutableLiveData<LoadableResult<MonitoringAttribute>>()
    val bloodPressureLiveData: LiveData<LoadableResult<MonitoringAttribute>> = _bloodPressureLiveData

    /** Получить данные о весе в виде QuerySnapshot */
    fun getWeightSnapshot() {
        _weightSnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(
                GetHealthMonitoringUseCase.Params(
                    MonitoringType.WEIGHT
                )
            )
        )
    }

    /** Получить данные о весе из QuerySnapshot */
    fun getWeight(snapshot: QuerySnapshot) {
        _weightLiveData.launchLoadData(
            parseSnapshotToMonitoringUseCase.executeFlow(
                ParseSnapshotToMonitoringUseCase.Params(snapshot)
            )
        )
    }

    /** Получить данные о росте в виде QuerySnapshot */
    fun getHeightSnapshot() {
        _heightSnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(
                GetHealthMonitoringUseCase.Params(
                    MonitoringType.HEIGHT
                )
            )
        )
    }

    /** Получить данные о росте из QuerySnapshot */
    fun getHeight(snapshot: QuerySnapshot) {
        _heightLiveData.launchLoadData(
            parseSnapshotToMonitoringUseCase.executeFlow(
                ParseSnapshotToMonitoringUseCase.Params(snapshot)
            )
        )
    }

    /** Получить данные о температуре в виде QuerySnapshot */
    fun getTemperatureSnapshot() {
        _temperatureSnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(
                GetHealthMonitoringUseCase.Params(
                    MonitoringType.TEMPERATURE
                )
            )
        )
    }

    /** Получить данные о росте из QuerySnapshot */
    fun getTemperature(snapshot: QuerySnapshot) {
        _temperatureLiveData.launchLoadData(
            parseSnapshotToMonitoringUseCase.executeFlow(
                ParseSnapshotToMonitoringUseCase.Params(snapshot)
            )
        )
    }

    /** Получить данные о давлении в виде QuerySnapshot */
    fun getBloodPressureSnapshot() {
        _bloodPressureSnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(
                GetHealthMonitoringUseCase.Params(
                    MonitoringType.BLOOD_PRESSURE
                )
            )
        )
    }

    /** Получить данные о давлении из QuerySnapshot */
    fun getBloodPressure(snapshot: QuerySnapshot) {
        _bloodPressureLiveData.launchLoadData(
            parseSnapshotToMonitoringUseCase.executeFlow(
                ParseSnapshotToMonitoringUseCase.Params(snapshot)
            )
        )
    }

    /** Открыть данные об атрибуте здоровья */
    fun openAttribute(healthMonitoring: MonitoringAttribute) {
        navigate(destinations.attribute(healthMonitoring))
    }
}