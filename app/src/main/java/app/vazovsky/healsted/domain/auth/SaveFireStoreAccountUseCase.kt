package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.mapper.AccountMapper
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Сохранение аккаунта в FireStore */
class SaveFireStoreAccountUseCase @Inject constructor(
    private val accountMapper: AccountMapper,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<SaveFireStoreAccountUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        return firebaseAuthRepository.saveAccount(params.uid, accountMapper.fromModelToDocument(params.account))
    }

    data class Params(
        /** UID аккаунта */
        val uid: String,

        /** Данные аккаунта */
        val account: Account,
    )
}