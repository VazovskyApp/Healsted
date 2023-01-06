package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import javax.inject.Inject

const val USERS_COLLECTION = "users"
const val ACCOUNTS_COLLECTION = "accounts"

class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : FirebaseAuthService {
    override fun signUpUser(
        email: String, password: String, nickname: String
    ) = firebaseAuth.createUserWithEmailAndPassword(email, password)


    override fun signInUser(
        email: String, password: String
    ) = firebaseAuth.signInWithEmailAndPassword(email, password)


    override fun signInWithGoogle(account: GoogleSignInAccount) =
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))


    override fun saveUser(
        email: String,
        phoneNumber: String,
    ) = firestore.collection(USERS_COLLECTION).document(email).set(
        User(
            email = email,
            phoneNumber = phoneNumber,
        )
    )

    override fun saveAccount(
        accountHolder: User,
        nickname: String,
        name: String,
        surname: String,
        patronymic: String,
        birthday: LocalDate?,
        avatar: String?,
        level: AccountLevel,
    ) = firestore.collection(ACCOUNTS_COLLECTION).document(accountHolder.email).set(
        Account(
            accountHolder = accountHolder,
            nickname = nickname,
            name = name,
            surname = surname,
            patronymic = patronymic,
            birthday = birthday,
            avatar = avatar,
            level = level,
        )
    )

    override fun fetchAccount(email: String) = firestore.collection(ACCOUNTS_COLLECTION).document(email).get()

    override fun fetchUsers() = firestore.collection(USERS_COLLECTION).get()

    override fun fetchAccounts() = firestore.collection(ACCOUNTS_COLLECTION).get()

    override fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)
}