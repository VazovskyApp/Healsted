package app.vazovsky.healsted.data.firebase.auth

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : FirebaseAuthRepository {

    //<editor-fold desc="FirebaseAuth">
    override fun getCurrentUserUid() = firebaseAuthService.getCurrentUserUid()

    override fun signUpUser(email: String, password: String) = firebaseAuthService.signUpUser(email, password)

    override fun signInUser(email: String, password: String) = firebaseAuthService.signInUser(email, password)

    override fun signInWithGoogle(account: GoogleSignInAccount) = firebaseAuthService.signInWithGoogle(account)

    override fun logOut() = firebaseAuthService.logOut()

    override fun deleteAccountFromFirebaseAuth() = firebaseAuthService.deleteAccountFromFirebaseAuth()
    //</editor-fold>

    //<editor-fold desc="FireStore">
    override fun saveUser(uid: String, user: User) = firebaseAuthService.saveUser(uid, user)

    override fun saveAccount(uid: String, account: Account) = firebaseAuthService.saveAccount(uid, account)

    override fun updateAccount(uid: String, account: Account) = firebaseAuthService.updateAccount(uid, account)

    override fun deleteAccount(uid: String) = firebaseAuthService.deleteAccount(uid)

    override fun fetchAccount(uid: String) = firebaseAuthService.fetchAccount(uid)

    override fun fetchUsers() = firebaseAuthService.fetchUsers()

    override fun fetchAccounts() = firebaseAuthService.fetchAccounts()
    //</editor-fold>

}