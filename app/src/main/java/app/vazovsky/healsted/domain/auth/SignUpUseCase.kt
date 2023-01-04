package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/** Регистрация */
class SignUpUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<SignUpUseCase.Params, Task<AuthResult>>() {

    override suspend fun execute(params: Params): Task<AuthResult> {
        return firebaseAuthRepository.signUpUser(params.email, params.password, params.nickname)
    }

    data class Params(
        /** Никнейм для регистрации */
        val nickname: String,

        /** Почта для регистрации */
        val email: String,

        /** Пароль для регистрации */
        val password: String,
    )
}