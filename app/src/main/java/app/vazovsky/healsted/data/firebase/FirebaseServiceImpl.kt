package app.vazovsky.healsted.data.firebase

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : FirebaseService {
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
    ) = firestore.collection("users").document(email).set(
        User(
            email = email,
            phoneNumber = "",
        )
    )

    override fun saveAccount(
        accountHolder: User,
        nickname: String,
    ) = firestore.collection("accounts").document(accountHolder.email).set(
        Account(
            accountHolder = accountHolder,
            nickname = nickname,
        )
    )

    override fun fetchUser() = firestore.collection("users").get()

    override fun fetchAccount() = firestore.collection("accounts").get()

    override fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)
}