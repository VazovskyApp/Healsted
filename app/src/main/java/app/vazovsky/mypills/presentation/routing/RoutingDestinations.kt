package app.vazovsky.mypills.presentation.routing

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class RoutingDestinations @Inject constructor() {
    fun onboarding() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToOnBoardingFragment()
    )

    fun auth() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToAuthFragment()
    )

    fun dashboard() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToDashboardGraph()
    )
}