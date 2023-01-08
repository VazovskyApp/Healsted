package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Выход из аккаунта */
class SignOutUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<UseCase.None, Unit>() {
    override suspend fun execute(params: UseCase.None) {
        authRepository.setCurrentUserUid(null)
        authRepository.setIsAuthorized(false)
        firebaseAuthRepository.logOut()
    }
}