package app.vazovsky.healsted.presentation.auth.login

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val destinations: LogInDestinations,
) : BaseViewModel() {
    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}