package app.vazovsky.mypills.presentation.auth

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class AuthDestinations @Inject constructor() {
    fun verification() = NavigationCommand.To(
        AuthFragmentDirections.actionAuthFragmentToVerificationFragment()
    )
}