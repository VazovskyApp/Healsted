package app.vazovsky.healsted.presentation.profile.routing

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class ProfileRoutingDestinations @Inject constructor() {

    fun auth() = NavigationCommand.To(
        ProfileRoutingFragmentDirections.actionProfileRoutingFragmentToAuthGraph()
    )

    fun profile() = NavigationCommand.To(
        ProfileRoutingFragmentDirections.actionProfileRoutingFragmentToProfileFragment()
    )
}