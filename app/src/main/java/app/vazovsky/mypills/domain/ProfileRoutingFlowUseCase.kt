package app.vazovsky.mypills.domain

import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

class ProfileRoutingFlowUseCase @Inject constructor() : UseCaseUnary<UseCase.None, ProfileRoutingResult>() {
    override suspend fun execute(params: UseCase.None): ProfileRoutingResult {
        return ProfileRoutingResult.AUTH
    }
}

enum class ProfileRoutingResult {
    PROFILE,
    AUTH,
}