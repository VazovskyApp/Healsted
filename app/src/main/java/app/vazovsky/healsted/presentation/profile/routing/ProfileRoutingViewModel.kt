package app.vazovsky.healsted.presentation.profile.routing

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.routing.ProfileRoutingFlowUseCase
import app.vazovsky.healsted.domain.routing.ProfileRoutingResult
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileRoutingViewModel @Inject constructor(
    private val destinations: ProfileRoutingDestinations,
    private val profileRoutingFlowUseCase: ProfileRoutingFlowUseCase,
) : BaseViewModel() {

    private val _initLiveEvent = SingleLiveEvent<LoadableResult<ProfileRoutingResult>>()
    val initLiveEvent: LiveData<LoadableResult<ProfileRoutingResult>> = _initLiveEvent

    fun runIntroFlow() {
        _initLiveEvent.launchLoadData(
            profileRoutingFlowUseCase.executeFlow(UseCase.None)
        )
    }

    fun navigateToResult(result: ProfileRoutingResult) {
        val destination = when (result) {
            ProfileRoutingResult.AUTH -> destinations.auth()
            ProfileRoutingResult.PROFILE -> destinations.profile()
        }
        navigate(destination)
    }
}