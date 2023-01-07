package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
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
    override fun getCurrentUserUid() = firebaseAuth.currentUser?.uid


    override fun signUpUser(
        email: String, password: String
    ) = firebaseAuth.createUserWithEmailAndPassword(email, password)


    override fun signInUser(
        email: String, password: String
    ) = firebaseAuth.signInWithEmailAndPassword(email, password)


    override fun signInWithGoogle(account: GoogleSignInAccount) =
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))


    override fun saveUser(
        uid: String,
        email: String,
        phoneNumber: String,
    ) = firestore.collection(USERS_COLLECTION).document(uid).set(
        User(
            email = email,
            phoneNumber = phoneNumber,
        )
    )

    override fun saveAccount(
        uid: String,
        accountHolder: User,
        nickname: String,
        name: String,
        surname: String,
        patronymic: String,
        birthday: LocalDate?,
        avatar: String?,
    ) = firestore.collection(ACCOUNTS_COLLECTION).document(uid).set(
        Account(
            accountHolder = accountHolder,
            nickname = nickname,
            name = name,
            surname = surname,
            patronymic = patronymic,
            birthday = birthday,
            avatar = avatar,
        )
    )

    override fun fetchAccount(
        uid: String
    ) = firestore.collection(ACCOUNTS_COLLECTION).document(uid).get()

    override fun fetchUsers() = firestore.collection(USERS_COLLECTION).get()

    override fun fetchAccounts() = firestore.collection(ACCOUNTS_COLLECTION).get()

    /** Тут скорей всего update */
    override fun editAccount(account: Account) = firestore
        .collection(ACCOUNTS_COLLECTION)
        .document(account.accountHolder.email)
        .set(account)

    override fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)
}