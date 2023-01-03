package app.vazovsky.healsted.domain.routing

import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Роутинг для главного графа */
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