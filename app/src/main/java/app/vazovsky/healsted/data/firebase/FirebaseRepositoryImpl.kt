package app.vazovsky.healsted.data.firebase

import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.time.LocalDate
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : FirebaseRepository {

    override fun signUpUser(
        email: String,
        password: String,
        nickname: String
    ) = firebaseService.signUpUser(email, password, nickname)

    override fun signInUser(
        email: String,
        password: String
    ) = firebaseService.signInUser(email, password)

    override fun signInWithGoogle(account: GoogleSignInAccount) = firebaseService.signInWithGoogle(account)

    override fun saveUser(
        email: String,
        phoneNumber: String,
    ) = firebaseService.saveUser(email, phoneNumber)

    override fun saveAccount(
        accountHolder: User,
        nickname: String,
        name: String,
        surname: String,
        patronymic: String,
        birthday: LocalDate?,
        avatar: String?,
        level: AccountLevel,
    ) = firebaseService.saveAccount(
        accountHolder = accountHolder,
        nickname = nickname,
        name = name,
        surname = surname,
        patronymic = patronymic,
        birthday = birthday,
        avatar = avatar,
        level = level
    )

    override fun fetchUser() = firebaseService.fetchUser()

    override fun fetchAccount() = firebaseService.fetchAccount()

    override fun sendForgotPassword(email: String) = firebaseService.sendForgotPassword(email)
}