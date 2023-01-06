package app.vazovsky.healsted.domain.routing

import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import timber.log.Timber

/** Роутинг для главного графа */
class RoutingFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCaseUnary<UseCase.None, RoutingResult>() {
    override suspend fun execute(params: UseCase.None): RoutingResult {
        val isAuthorized = authRepository.getIsAuthorized()
        // val isOnBoardingShown = authRepository.getIsOnBoardingShowed()
        return if (isAuthorized) {
            RoutingResult.DASHBOARD
        } else RoutingResult.AUTH
    }
}

/** Варианты главного роутинга */
enum class RoutingResult {
    ONBOARDING,
    DASHBOARD,
    AUTH,
}