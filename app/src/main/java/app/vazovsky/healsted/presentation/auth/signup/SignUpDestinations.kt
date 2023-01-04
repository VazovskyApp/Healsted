package app.vazovsky.healsted.presentation.auth.signup

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class SignUpDestinations @Inject constructor() {
    fun dashboard() = NavigationCommand.To(
        SignUpFragmentDirections.actionSignUpFragmentToDashboardGraph()
    )
}