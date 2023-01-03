package app.vazovsky.healsted.presentation.auth

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class AuthDestinations @Inject constructor() {
    fun verification() = NavigationCommand.To(
        AuthFragmentDirections.actionAuthFragmentToVerificationFragment()
    )
}