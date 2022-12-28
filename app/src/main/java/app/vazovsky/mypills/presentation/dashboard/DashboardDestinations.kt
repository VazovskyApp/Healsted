package app.vazovsky.mypills.presentation.dashboard

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class DashboardDestinations @Inject constructor() {
    fun addPill() = NavigationCommand.To(
        DashboardFragmentDirections.actionDashboardFragmentToAddPill()
    )
}