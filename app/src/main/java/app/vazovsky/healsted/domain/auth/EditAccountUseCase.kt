package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Редактирование аккаунта */
class EditAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<EditAccountUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseAuthRepository.updateAccount(uid, params.account)
    }

    data class Params(
        val account: Account,
    )
}