package app.vazovsky.mypills.presentation.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.data.model.base.LoadableResult
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.health.GetHealthMonitoringUseCase
import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val getHealthMonitoringUseCase: GetHealthMonitoringUseCase,
    private val healthDestinations: HealthDestinations,
) : BaseViewModel() {

    /** Информация о мониторинге здоровья */
    private val _healthLiveData = MutableLiveData<LoadableResult<List<MonitoringAttribute>>>()
    val healthLiveData: LiveData<LoadableResult<List<MonitoringAttribute>>> = _healthLiveData

    fun getHealth() {
        _healthLiveData.launchLoadData(getHealthMonitoringUseCase.executeFlow(UseCase.None))
    }

    fun openAttribute(healthMonitoring: MonitoringAttribute) {
        navigate(healthDestinations.attribute(healthMonitoring))
    }
}