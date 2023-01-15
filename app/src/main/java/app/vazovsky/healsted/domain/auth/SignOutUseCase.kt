package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import javax.inject.Inject

/** Выход из аккаунта FirebaseAuth */
class SignOutUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<UseCase.None, SignOutUseCase.Result>() {
    override suspend fun execute(params: UseCase.None): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        authRepository.setCurrentUserUid(null)
        authRepository.setIsAuthorized(false)

        firebaseAuthRepository.logOut()
        return Result(uid)
    }

    data class Result(
        val uid: String,
    )
}