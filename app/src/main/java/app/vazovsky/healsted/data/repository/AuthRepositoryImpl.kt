package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.preferences.account.AccountPreferencesStorage
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesStorage: AccountPreferencesStorage,
) : AuthRepository {

    //<editor-fold desc="Authorized">
    override fun getIsAuthorized() = preferencesStorage.isAccountAuthorized

    override fun setIsAuthorized(isAuthorized: Boolean) {
        preferencesStorage.isAccountAuthorized = isAuthorized
    }
    //</editor-fold>

    //<editor-fold desc="OnBoarding Showed">
    override fun getIsOnBoardingShowed() = preferencesStorage.isOnBoardingShown

    override fun setIsOnBoardingShowed(isOnBoardingShowed: Boolean) {
        preferencesStorage.isOnBoardingShown = isOnBoardingShowed
    }
    //</editor-fold>

    //<editor-fold desc="Current User UID">
    override fun getCurrentUserUid() = preferencesStorage.currentUserUid

    override fun setCurrentUserUid(userUid: String?) {
        preferencesStorage.currentUserUid = userUid
    }
    //</editor-fold>

}