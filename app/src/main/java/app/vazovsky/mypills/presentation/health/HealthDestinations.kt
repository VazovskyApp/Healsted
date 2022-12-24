package app.vazovsky.mypills.presentation.health

import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class HealthDestinations @Inject constructor() {
    fun attribute(healthMonitoring: MonitoringAttribute) = NavigationCommand.To(
        HealthFragmentDirections.actionHealthFragmentToHealthAttributeFragment(healthMonitoring = healthMonitoring)
    )
}