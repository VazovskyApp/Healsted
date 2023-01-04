package app.vazovsky.healsted.domain.profile

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.repository.ProfileRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получить данные об аккаунте */
class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) : UseCaseUnary<UseCase.None, Account>() {
    override suspend fun execute(params: UseCase.None): Account {
        return profileRepository.getProfile()
    }
}