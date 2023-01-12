package app.vazovsky.healsted.presentation.health.attribute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.health.GetHealthMonitoringUseCase
import app.vazovsky.healsted.domain.health.ParseSnapshotToMonitoringHistoryUseCase
import app.vazovsky.healsted.domain.health.ParseSnapshotToMonitoringUseCase
import app.vazovsky.healsted.domain.health.AddMonitoringAttributeUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthAttributeViewModel @Inject constructor(
    private val getHealthMonitoringUseCase: GetHealthMonitoringUseCase,
    private val parseSnapshotToMonitoringUseCase: ParseSnapshotToMonitoringUseCase,
    private val parseSnapshotToMonitoringHistoryUseCase: ParseSnapshotToMonitoringHistoryUseCase,
    private val addMonitoringAttributeUseCase: AddMonitoringAttributeUseCase,
) : BaseViewModel() {

    /** Информация об атрибуте мониторинга в виде QuerySnapshot */
    private val _monitoringSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val monitoringSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _monitoringSnapshotLiveData

    /** Информация об атрибуте мониторинга из QuerySnapshot */
    private val _monitoringLiveData = MutableLiveData<LoadableResult<MonitoringAttribute>>()
    val monitoringLiveData: LiveData<LoadableResult<MonitoringAttribute>> = _monitoringLiveData

    /** История мониторинга в виде QuerySnapshot */
    private val _historySnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val historySnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _historySnapshotLiveData

    /** История мониторинга из QuerySnapshot */
    private val _historyLiveData = MutableLiveData<LoadableResult<List<MonitoringAttribute>>>()
    val historyLiveData: LiveData<LoadableResult<List<MonitoringAttribute>>> = _historyLiveData

    /** Сохранить новое значение атрибута мониторинга */
    private val _updateMonitoringLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val updateMonitoringLiveEvent: LiveData<LoadableResult<Task<Void>>> = _updateMonitoringLiveEvent

    fun getMonitoringSnapshot(type: MonitoringType) {
        _monitoringSnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(
                GetHealthMonitoringUseCase.Params(type)
            )
        )
    }

    /** Получить данные о давлении из QuerySnapshot */
    fun getMonitoring(snapshot: QuerySnapshot) {
        _monitoringLiveData.launchLoadData(
            parseSnapshotToMonitoringUseCase.executeFlow(
                ParseSnapshotToMonitoringUseCase.Params(snapshot)
            )
        )
    }

    /** Получение истории мониторинга в виде QuerySnapshot */
    fun getHistorySnapshot(type: MonitoringType) {
        _historySnapshotLiveData.launchLoadData(
            getHealthMonitoringUseCase.executeFlow(GetHealthMonitoringUseCase.Params(type))
        )
    }

    /** Получение истории мониторинга */
    fun getHistory(snapshot: QuerySnapshot) {
        _historyLiveData.launchLoadData(
            parseSnapshotToMonitoringHistoryUseCase.executeFlow(ParseSnapshotToMonitoringHistoryUseCase.Params(snapshot))
        )
    }

    /** Добавить новое значение здоровья */
    fun updateMonitoring(monitoringAttribute: MonitoringAttribute) {
        _updateMonitoringLiveEvent.launchLoadData(
            addMonitoringAttributeUseCase.executeFlow(AddMonitoringAttributeUseCase.Params(monitoringAttribute))
        )
    }
}