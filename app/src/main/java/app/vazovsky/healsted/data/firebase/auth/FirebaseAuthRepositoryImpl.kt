package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : FirebaseAuthRepository {

    override fun signUpUser(
        email: String,
        password: String,
    ) = firebaseAuthService.signUpUser(email, password)

    override fun signInUser(
        email: String,
        password: String
    ) = firebaseAuthService.signInUser(email, password)

    override fun signInWithGoogle(account: GoogleSignInAccount) = firebaseAuthService.signInWithGoogle(account)

    override fun saveUser(
        uid: String,
        email: String,
        phoneNumber: String,
    ) = firebaseAuthService.saveUser(uid, email, phoneNumber)

    override fun saveAccount(
        uid: String,
        accountHolder: User,
        nickname: String,
        name: String,
        surname: String,
        patronymic: String,
        birthday: LocalDate?,
        avatar: String?,
    ) = firebaseAuthService.saveAccount(
        uid = uid,
        accountHolder = accountHolder,
        nickname = nickname,
        name = name,
        surname = surname,
        patronymic = patronymic,
        birthday = birthday,
        avatar = avatar,
    )

    override fun fetchAccount(uid: String) = firebaseAuthService.fetchAccount(uid)

    override fun fetchUsers() = firebaseAuthService.fetchUsers()

    override fun fetchAccounts() = firebaseAuthService.fetchAccounts()
    override fun editAccount(account: Account): Task<Void> = firebaseAuthService.editAccount(account)

    override fun sendForgotPassword(email: String) = firebaseAuthService.sendForgotPassword(email)
}