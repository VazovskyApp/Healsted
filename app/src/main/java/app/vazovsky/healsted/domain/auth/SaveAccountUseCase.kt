package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.FirebaseRepository
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import javax.inject.Inject

class SaveAccountUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) : UseCaseUnary<SaveAccountUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        return firebaseRepository.saveAccount(
            params.accountHolder,
            params.nickname,
            params.name,
            params.surname,
            params.patronymic,
            params.birthday,
            params.avatar,
            params.level,
        )
    }

    data class Params(
        val accountHolder: User,
        val nickname: String,
        val name: String,
        val surname: String,
        val patronymic: String,
        val birthday: LocalDate?,
        val avatar: String?,
        val level: AccountLevel,
    )
}