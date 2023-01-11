package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseAuthRepository {

    fun getCurrentUserUid(): String?

    fun signUpUser(
        email: String,
        password: String,
    ): Task<AuthResult>

    fun signInUser(
        email: String,
        password: String,
    ): Task<AuthResult>

    fun signInWithGoogle(
        account: GoogleSignInAccount,
    ): Task<AuthResult>

    fun logOut()

    fun deleteAccountFromFirebaseAuth() : Task<Void>?

    fun saveUser(
        uid: String,
        email: String,
        phoneNumber: String,
    ): Task<Void>

    fun saveAccount(
        uid: String,
        accountHolder: User,
        nickname: String,
        name: String = "",
        surname: String = "",
        patronymic: String = "",
        birthday: Timestamp?,
        avatar: String?,
    ): Task<Void>

    fun updateAccount(
        uid: String,
        account: Account,
    ): Task<Void>

    fun deleteAccount(
        uid: String,
    ) : Task<Void>

    fun fetchAccount(uid: String): Task<DocumentSnapshot>

    fun fetchUsers(): Task<QuerySnapshot>

    fun fetchAccounts(): Task<QuerySnapshot>

    fun sendForgotPassword(email: String): Task<Void>
}