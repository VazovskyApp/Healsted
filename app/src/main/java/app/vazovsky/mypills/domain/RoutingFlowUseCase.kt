package app.vazovsky.mypills.domain

import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

class RoutingFlowUseCase @Inject constructor() : UseCaseUnary<UseCase.None, RoutingResult>() {
    override suspend fun execute(params: UseCase.None): RoutingResult {
        return RoutingResult.DASHBOARD
    }
}

enum class RoutingResult {
    ONBOARDING,
    DASHBOARD,
    AUTH,
}