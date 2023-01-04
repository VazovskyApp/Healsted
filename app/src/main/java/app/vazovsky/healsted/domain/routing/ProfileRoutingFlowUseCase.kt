package app.vazovsky.healsted.domain.routing

import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Роутинг для графа профиля */
class ProfileRoutingFlowUseCase @Inject constructor() : UseCaseUnary<UseCase.None, ProfileRoutingResult>() {
    override suspend fun execute(params: UseCase.None): ProfileRoutingResult {
        return ProfileRoutingResult.PROFILE
    }
}

/** Варианты роутинга профиля */
enum class ProfileRoutingResult {
    PROFILE,
    AUTH,
}