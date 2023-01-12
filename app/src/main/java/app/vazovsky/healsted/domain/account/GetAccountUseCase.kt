package app.vazovsky.healsted.domain.account

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

/** Получение аккаунта в виде DocumentSnapshot */
class GetAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<UseCase.None, Task<DocumentSnapshot>>() {
    override suspend fun execute(params: UseCase.None): Task<DocumentSnapshot> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseAuthRepository.fetchAccount(uid)
    }
}