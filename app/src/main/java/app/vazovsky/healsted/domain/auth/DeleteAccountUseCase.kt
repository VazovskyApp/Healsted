package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Удаление аккаунта из Firebase */
class DeleteAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, DeleteAccountUseCase.Result>() {
    override suspend fun execute(params: UseCase.None): Result {
        return Result(firebaseAuthRepository.deleteAccountFromFirebaseAuth())
    }

    data class Result(
        /** Task с результатом удаления аккаунта */
        val task: Task<Void>?,
    )
}