package app.vazovsky.healsted.presentation.auth

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class AuthDestinations @Inject constructor() {
    fun logIn() = NavigationCommand.To(
        AuthFragmentDirections.actionAuthFragmentToLogInFragment()
    )

    fun signUp() = NavigationCommand.To(
        AuthFragmentDirections.actionAuthFragmentToSignUpFragment()
    )
}