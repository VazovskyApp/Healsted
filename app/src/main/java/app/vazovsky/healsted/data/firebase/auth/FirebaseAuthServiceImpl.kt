package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.extensions.serializeToMap
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val USERS_COLLECTION = "users"
const val ACCOUNTS_COLLECTION = "accounts"

class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : FirebaseAuthService {

    //<editor-fold desc="FirebaseAuth">
    override fun getCurrentUserUid() = firebaseAuth.currentUser?.uid

    override fun signUpUser(
        email: String,
        password: String,
    ) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun signInUser(
        email: String,
        password: String,
    ) = firebaseAuth.signInWithEmailAndPassword(email, password)

    override fun signInWithGoogle(
        account: GoogleSignInAccount
    ) = firebaseAuth.signInWithCredential(
        GoogleAuthProvider.getCredential(account.idToken, null)
    )

    override fun logOut() = firebaseAuth.signOut()

    /** TODO не удаляется аккаунт из Firebase */
    override fun deleteAccountFromFirebaseAuth() = firebaseAuth.currentUser?.delete()
    //</editor-fold>

    //<editor-fold desc="FireStore">
    override fun saveUser(
        uid: String,
        user: User,
    ) = firestore.collection(USERS_COLLECTION)
        .document(uid)
        .set(user)

    override fun saveAccount(
        uid: String,
        account: Account,
    ) = firestore.collection(ACCOUNTS_COLLECTION)
        .document(uid)
        .set(account)

    override fun updateAccount(
        uid: String,
        account: Account,
    ) = firestore.collection(ACCOUNTS_COLLECTION)
        .document(uid)
        .update(account.serializeToMap())

    override fun deleteAccount(uid: String) = firestore.collection(ACCOUNTS_COLLECTION).document(uid).delete()

    override fun fetchAccount(uid: String) = firestore.collection(ACCOUNTS_COLLECTION).document(uid).get()

    override fun fetchUsers() = firestore.collection(USERS_COLLECTION).get()

    override fun fetchAccounts() = firestore.collection(ACCOUNTS_COLLECTION).get()
    //</editor-fold>

}