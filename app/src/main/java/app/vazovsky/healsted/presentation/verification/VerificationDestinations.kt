package app.vazovsky.healsted.presentation.verification

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class VerificationDestinations @Inject constructor() {
    fun dashboard() = NavigationCommand.To(
        VerificationFragmentDirections.actionVerificationFragmentToDashboardGraph()
    )
}