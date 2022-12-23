package app.vazovsky.mypills.presentation.verification

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class VerificationDestinations @Inject constructor() {
    fun dashboard() = NavigationCommand.To(
        VerificationFragmentDirections.actionVerificationFragmentToDashboardGraph()
    )
}