package app.vazovsky.mypills.presentation.verification

import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verificationDestinations: VerificationDestinations,
) : BaseViewModel() {
    fun openDashboard() {
        navigate(verificationDestinations.dashboard())
    }
}