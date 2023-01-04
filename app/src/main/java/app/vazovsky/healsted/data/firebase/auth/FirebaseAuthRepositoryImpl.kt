package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.time.LocalDate
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : FirebaseAuthRepository {

    override fun signUpUser(
        email: String,
        password: String,
        nickname: String
    ) = firebaseAuthService.signUpUser(email, password, nickname)

    override fun signInUser(
        email: String,
        password: String
    ) = firebaseAuthService.signInUser(email, password)

    override fun signInWithGoogle(account: GoogleSignInAccount) = firebaseAuthService.signInWithGoogle(account)

    override fun saveUser(
        email: String,
        phoneNumber: String?,
    ) = firebaseAuthService.saveUser(email, phoneNumber)

    override fun saveAccount(
        accountHolder: User,
        nickname: String,
        name: String,
        surname: String,
        patronymic: String,
        birthday: LocalDate?,
        avatar: String?,
        level: AccountLevel,
    ) = firebaseAuthService.saveAccount(
        accountHolder = accountHolder,
        nickname = nickname,
        name = name,
        surname = surname,
        patronymic = patronymic,
        birthday = birthday,
        avatar = avatar,
        level = level
    )

    override fun fetchUser() = firebaseAuthService.fetchUser()

    override fun fetchAccount() = firebaseAuthService.fetchAccount()

    override fun sendForgotPassword(email: String) = firebaseAuthService.sendForgotPassword(email)
}