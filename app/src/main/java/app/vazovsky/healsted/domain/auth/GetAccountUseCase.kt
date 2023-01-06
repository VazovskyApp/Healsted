package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

/** Получение аккаунта из FireStore */
class GetAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<GetAccountUseCase.Params, Task<DocumentSnapshot>>() {

    override suspend fun execute(params: Params): Task<DocumentSnapshot> {
        return firebaseAuthRepository.fetchAccount(params.email)
    }

    data class Params(
        val email: String,
    )
}