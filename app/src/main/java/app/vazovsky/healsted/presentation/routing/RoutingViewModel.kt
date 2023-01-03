package app.vazovsky.healsted.presentation.routing

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.routing.RoutingFlowUseCase
import app.vazovsky.healsted.domain.routing.RoutingResult
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutingViewModel @Inject constructor(
    private val routingDestinations: RoutingDestinations,
    private val routingFlowUseCase: RoutingFlowUseCase,
) : BaseViewModel() {

    private val _initLiveEvent = SingleLiveEvent<LoadableResult<RoutingResult>>()
    val initLiveEvent: LiveData<LoadableResult<RoutingResult>> = _initLiveEvent

    fun runIntroFlow() {
        _initLiveEvent.launchLoadData(
            routingFlowUseCase.executeFlow(UseCase.None)
        )
    }

    fun navigateToResult(result: RoutingResult) {
        val destination = when (result) {
            RoutingResult.ONBOARDING -> routingDestinations.onboarding()
            RoutingResult.DASHBOARD -> routingDestinations.dashboard()
            RoutingResult.AUTH -> routingDestinations.auth()
        }
        navigate(destination)
    }
}