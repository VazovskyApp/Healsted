package app.vazovsky.mypills.presentation.dashboard

import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardDestinations: DashboardDestinations,
) : BaseViewModel() {
    fun openAddPill() {
        navigate(dashboardDestinations.addPill())
    }
}