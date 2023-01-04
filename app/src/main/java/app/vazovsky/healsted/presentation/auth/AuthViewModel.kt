package app.vazovsky.healsted.presentation.auth

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val destinations: AuthDestinations,
) : BaseViewModel() {
    fun openVerification() {
        navigate(destinations.verification())
    }
}