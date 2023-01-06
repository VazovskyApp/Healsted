package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate

interface FirebaseAuthService {

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
    ): Task<Void>

    fun fetchAccount(email: String): Task<DocumentSnapshot>

    fun fetchUsers(): Task<QuerySnapshot>

    fun fetchAccounts(): Task<QuerySnapshot>

    fun editAccount(account: Account): Task<Void>

    fun sendForgotPassword(email: String): Task<Void>
}