package app.vazovsky.healsted.presentation.auth.signup

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val destinations: SignUpDestinations,
) : BaseViewModel() {
    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}