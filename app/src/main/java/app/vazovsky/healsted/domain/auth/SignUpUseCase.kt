package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/** Регистрация */
class SignUpUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val authRepository: AuthRepository,
) : UseCaseUnary<SignUpUseCase.Params, Task<AuthResult>>() {

    override suspend fun execute(params: Params): Task<AuthResult> {
        val result = firebaseAuthRepository.signUpUser(params.email, params.password)

        result.addOnSuccessListener {
            authRepository.setIsAuthorized(true)
            authRepository.setCurrentUserUid(result.result.user?.uid)
        }

        return result
    }

    data class Params(
        /** Почта для регистрации */
        val email: String,

        /** Пароль для регистрации */
        val password: String,
    )
}