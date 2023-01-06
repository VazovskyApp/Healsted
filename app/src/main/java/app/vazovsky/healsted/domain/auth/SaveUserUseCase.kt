package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Сохранение пользователя в FireStore */
class SaveUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<SaveUserUseCase.Params, SaveUserUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        return Result(
            task = firebaseAuthRepository.saveUser(params.email, params.phoneNumber),
            email = params.email,
        )
    }

    data class Params(
        /** Email, указанный при регистрации */
        val email: String,

        /** Номер телефона */
        val phoneNumber: String = "",
    )

    data class Result(
        /** Результат сохранения пользователя */
        val task: Task<Void>,

        /** Указанный Email */
        val email: String,
    )
}