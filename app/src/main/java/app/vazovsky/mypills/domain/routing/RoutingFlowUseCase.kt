package app.vazovsky.mypills.domain.routing

import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Роутинг для главного графа */
class RoutingFlowUseCase @Inject constructor() : UseCaseUnary<UseCase.None, RoutingResult>() {
    override suspend fun execute(params: UseCase.None): RoutingResult {
        return RoutingResult.AUTH
    }
}

enum class RoutingResult {
    ONBOARDING,
    DASHBOARD,
    AUTH,
}