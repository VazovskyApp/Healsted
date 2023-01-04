package app.vazovsky.healsted.data.firebase

import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
    ) = firebaseService.saveUser(email)

    override fun saveAccount(
        accountHolder: User,
        nickname: String
    ) = firebaseService.saveAccount(accountHolder, nickname)

    override fun fetchUser() = firebaseService.fetchUser()

    override fun fetchAccount() = firebaseService.fetchAccount()

    override fun sendForgotPassword(email: String) = firebaseService.sendForgotPassword(email)
}