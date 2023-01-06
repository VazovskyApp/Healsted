package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.preferences.account.AccountPreferencesStorage
import javax.inject.Inject
import timber.log.Timber

class AuthRepositoryImpl @Inject constructor(
    private val preferencesStorage: AccountPreferencesStorage,
) : AuthRepository {

    override fun getIsAuthorized() = preferencesStorage.isAccountAuthorized

    override fun setIsAuthorized(isAuthorized: Boolean) {
        preferencesStorage.isAccountAuthorized = isAuthorized
        Timber.d("IsAuthorized = ${preferencesStorage.isAccountAuthorized}")
    }

    override fun getIsOnBoardingShowed() = preferencesStorage.isOnBoardingShown

    override fun setIsOnBoardingShowed(isOnBoardingShowed: Boolean) {
        preferencesStorage.isOnBoardingShown = isOnBoardingShowed
    }

    override fun getCurrentUserUid() = preferencesStorage.currentUserUid

    override fun setCurrentUserUid(userUid: String?) {
        preferencesStorage.currentUserUid = userUid
        Timber.d("CurrentUserUid = ${preferencesStorage.currentUserUid}")
    }
}