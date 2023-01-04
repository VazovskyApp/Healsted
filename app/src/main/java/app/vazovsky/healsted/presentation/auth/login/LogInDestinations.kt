package app.vazovsky.healsted.presentation.auth.login

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class LogInDestinations @Inject constructor() {
    fun dashboard() = NavigationCommand.To(
        LogInFragmentDirections.actionLogInFragmentToDashboardGraph()
    )
}