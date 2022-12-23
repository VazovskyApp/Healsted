package app.vazovsky.mypills.presentation.profile.routing

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class ProfileRoutingDestinations @Inject constructor() {

    fun auth() = NavigationCommand.To(
        ProfileRoutingFragmentDirections.actionProfileRoutingFragmentToAuthGraph()
    )

    fun profile() = NavigationCommand.To(
        ProfileRoutingFragmentDirections.actionProfileRoutingFragmentToProfileFragment()
    )
}