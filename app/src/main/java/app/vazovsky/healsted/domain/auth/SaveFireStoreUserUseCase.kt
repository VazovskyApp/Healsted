package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Сохранение пользователя в FireStore */
class SaveFireStoreUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<SaveFireStoreUserUseCase.Params, SaveFireStoreUserUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        return Result(
            task = firebaseAuthRepository.saveUser(params.uid, params.user),
            uid = params.uid,
            user = params.user,
        )
    }

    data class Params(
        /** UID текущего пользователя */
        val uid: String,

        /** Email, указанный при регистрации */
        val user: User,
    )

    data class Result(
        /** Результат сохранения пользователя */
        val task: Task<Void>,

        /** UID текущего пользователя  */
        val uid: String,

        /** Текущий пользователь */
        val user: User,
    )
}