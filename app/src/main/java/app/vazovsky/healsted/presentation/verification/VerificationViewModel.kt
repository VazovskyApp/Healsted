package app.vazovsky.healsted.presentation.verification

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val destinations: VerificationDestinations,
) : BaseViewModel() {

    /** Открытие дашборда */
    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}