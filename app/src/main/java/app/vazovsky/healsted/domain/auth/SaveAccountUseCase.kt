package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import javax.inject.Inject

/** Сохранение аккаунта в FireStore */
class SaveAccountUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<SaveAccountUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        return firebaseAuthRepository.saveAccount(
            uid = params.uid,
            accountHolder = params.accountHolder,
            nickname = params.nickname,
            name = params.name,
            surname = params.surname,
            patronymic = params.patronymic,
            birthday = params.birthday,
            avatar = params.avatar,
        )
    }

    data class Params(
        val uid: String,
        val accountHolder: User,
        val nickname: String,
        val name: String,
        val surname: String,
        val patronymic: String,
        val birthday: LocalDate?,
        val avatar: String?,
    )
}