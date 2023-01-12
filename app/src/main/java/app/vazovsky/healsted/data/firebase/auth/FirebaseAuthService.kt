package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseAuthService {

    //<editor-fold desc="FirebaseAuth">
    /** Получение UID текущего пользователя из FirebaseAuth */
    fun getCurrentUserUid(): String?

    /** Регистрация пользователя по Email в FirebaseAuth */
    fun signUpUser(email: String, password: String): Task<AuthResult>

    /** Авторизация пользователя по Email в FirebaseAuth */
    fun signInUser(email: String, password: String): Task<AuthResult>

    /** Авторизация пользователя через Google в FirebaseAuth */
    fun signInWithGoogle(account: GoogleSignInAccount): Task<AuthResult>

    /** Выход из аккаунта в FirebaseAuth */
    fun logOut()

    /** Удаление аккаунта из FirebaseAuth */
    fun deleteAccountFromFirebaseAuth(): Task<Void>?
    //</editor-fold>

    //<editor-fold desc="FireStore">
    /** Сохранение пользователя в FireStore */
    fun saveUser(uid: String, email: String, phoneNumber: String): Task<Void>

    /** Сохранение аккаунта в FireStore */
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

    /** Обновление аккаунта в FireStore */
    fun updateAccount(uid: String, account: Account): Task<Void>

    /** Удаление аккаунта из FireStore */
    fun deleteAccount(uid: String): Task<Void>

    /** Получение аккаунта по UID из FireStore */
    fun fetchAccount(uid: String): Task<DocumentSnapshot>

    /** Получение всех пользователей из FireStore */
    fun fetchUsers(): Task<QuerySnapshot>

    /** Получение всех аккаунтов из FireStore */
    fun fetchAccounts(): Task<QuerySnapshot>
    //</editor-fold>

}