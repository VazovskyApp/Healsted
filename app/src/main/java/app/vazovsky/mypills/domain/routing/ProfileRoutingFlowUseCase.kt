package app.vazovsky.mypills.domain.routing

import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Роутинг для графа профиля */
class ProfileRoutingFlowUseCase @Inject constructor() : UseCaseUnary<UseCase.None, ProfileRoutingResult>() {
    override suspend fun execute(params: UseCase.None): ProfileRoutingResult {
        return ProfileRoutingResult.AUTH
    }
}

enum class ProfileRoutingResult {
    PROFILE,
    AUTH,
}