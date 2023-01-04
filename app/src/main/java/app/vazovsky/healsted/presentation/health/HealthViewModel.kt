package app.vazovsky.healsted.presentation.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.health.GetHealthMonitoringUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val destinations: HealthDestinations,
    private val getHealthMonitoringUseCase: GetHealthMonitoringUseCase,
) : BaseViewModel() {

    /** Информация о мониторинге здоровья */
    private val _healthLiveData = MutableLiveData<LoadableResult<List<MonitoringAttribute>>>()
    val healthLiveData: LiveData<LoadableResult<List<MonitoringAttribute>>> = _healthLiveData

    /** Получить данные о здоровье */
    fun getHealth() {
        _healthLiveData.launchLoadData(getHealthMonitoringUseCase.executeFlow(UseCase.None))
    }

    /** Открыть данные об атрибуте здоровья */
    fun openAttribute(healthMonitoring: MonitoringAttribute) {
        navigate(destinations.attribute(healthMonitoring))
    }
}