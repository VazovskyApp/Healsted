package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Удаление аккаунта из Firebase */
class DeleteAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<UseCase.None, DeleteAccountUseCase.Result>() {
    override suspend fun execute(params: UseCase.None): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()
        return Result(
            task = firebaseAuthRepository.deleteAccountFromFirebaseAuth(),
            uid = uid,
        )
    }

    data class Result(
        /** Task с результатом удаления аккаунта */
        val task: Task<Void>?,
        val uid: String,
    )
}