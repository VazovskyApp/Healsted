package app.vazovsky.healsted.data.firebase

import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate
import java.time.LocalTime

interface FirebaseService {

    fun signUpUser(
        email: String,
        password: String,
        nickname: String,
    ): Task<AuthResult>

    fun signInUser(
        email: String,
        password: String,
    ): Task<AuthResult>

    fun signInWithGoogle(
        account: GoogleSignInAccount,
    ): Task<AuthResult>

    fun saveUser(
        email: String,
        phoneNumber: String,
    ): Task<Void>

    fun saveAccount(
        accountHolder: User,
        nickname: String,
        name: String = "",
        surname: String = "",
        patronymic: String = "",
        birthday: LocalDate?,
        avatar: String?,
        level: AccountLevel = AccountLevel.BACTERIA,
    ): Task<Void>

    fun fetchUser(): Task<QuerySnapshot>

    fun fetchAccount(): Task<QuerySnapshot>

    fun sendForgotPassword(email: String): Task<Void>
}