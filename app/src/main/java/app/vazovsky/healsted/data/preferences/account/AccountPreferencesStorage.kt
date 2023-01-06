package app.vazovsky.healsted.data.preferences.account

import app.vazovsky.healsted.data.preferences.base.RegularPreferenceStorage
import app.vazovsky.healsted.data.preferences.base.UserIndependentPreferenceStorage
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_IS_ON_BOARDING_SHOWN = "is_on_boarding_shown"
private const val KEY_IS_ACCOUNT_AUTHORIZED = "is_account_authorized"
private const val KEY_CURRENT_USER_UID = "current_user_uid"


@Singleton
class AccountPreferencesStorage @Inject constructor(
    private val userIndependentPreferenceStorage: UserIndependentPreferenceStorage,
    private val regularPreferenceStorage: RegularPreferenceStorage,
) {

    var isOnBoardingShown: Boolean
        get() = regularPreferenceStorage.getBoolean(KEY_IS_ON_BOARDING_SHOWN)
        set(value) = regularPreferenceStorage.edit().putBoolean(KEY_IS_ON_BOARDING_SHOWN, value).apply()

    var isAccountAuthorized: Boolean
        get() = regularPreferenceStorage.getBoolean(KEY_IS_ACCOUNT_AUTHORIZED)
        set(value) = regularPreferenceStorage.edit().putBoolean(KEY_IS_ACCOUNT_AUTHORIZED, value).apply()

    var currentUserUid: String?
        get() = regularPreferenceStorage.getString(KEY_CURRENT_USER_UID)
        set(value) = regularPreferenceStorage.edit().putString(KEY_CURRENT_USER_UID, value).apply()
}