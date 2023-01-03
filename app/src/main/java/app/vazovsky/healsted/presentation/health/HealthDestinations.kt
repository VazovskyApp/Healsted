package app.vazovsky.healsted.presentation.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class HealthDestinations @Inject constructor() {
    fun attribute(healthMonitoring: MonitoringAttribute) = NavigationCommand.To(
        HealthFragmentDirections.actionHealthFragmentToHealthAttributeFragment(healthMonitoring = healthMonitoring)
    )
}