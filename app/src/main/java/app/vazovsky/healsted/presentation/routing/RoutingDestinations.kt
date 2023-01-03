package app.vazovsky.healsted.presentation.routing

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class RoutingDestinations @Inject constructor() {
    fun onboarding() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToOnBoardingFragment()
    )

    fun auth() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToAuthGraph()
    )

    fun dashboard() = NavigationCommand.To(
        RoutingFragmentDirections.actionRoutingFragmentToDashboardGraph()
    )
}