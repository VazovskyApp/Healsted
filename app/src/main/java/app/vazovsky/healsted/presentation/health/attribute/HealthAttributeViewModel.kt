package app.vazovsky.healsted.presentation.health.attribute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.health.GetHealthMonitoringHistoryUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthAttributeViewModel @Inject constructor(
    private val getHealthMonitoringHistoryUseCase: GetHealthMonitoringHistoryUseCase,
) : BaseViewModel() {

    /** История мониторинга */
    private val _historyLiveData = MutableLiveData<LoadableResult<List<MonitoringAttribute>>>()
    val historyLiveData: LiveData<LoadableResult<List<MonitoringAttribute>>> = _historyLiveData

    /** Получение истории мониторинга */
    fun getHistory(type: MonitoringType) {
        _historyLiveData.launchLoadData(
            getHealthMonitoringHistoryUseCase.executeFlow(GetHealthMonitoringHistoryUseCase.Params(type))
        )
    }

}