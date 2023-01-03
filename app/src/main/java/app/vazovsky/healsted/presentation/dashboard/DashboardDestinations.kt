package app.vazovsky.healsted.presentation.dashboard

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class DashboardDestinations @Inject constructor() {
    fun addPill() = NavigationCommand.To(
        DashboardFragmentDirections.actionDashboardFragmentToAddPill()
    )
}