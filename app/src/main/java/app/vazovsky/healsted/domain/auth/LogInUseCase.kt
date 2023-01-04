package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.FirebaseRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/** Авторизация */
class LogInUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) : UseCaseUnary<LogInUseCase.Params, Task<AuthResult>>() {

    override suspend fun execute(params: Params): Task<AuthResult> {
        return firebaseRepository.signInUser(params.email, params.password)
    }

    data class Params(
        /** Почта для авторизации */
        val email: String,

        /** Пароль для авторизации */
        val password: String,
    )
}