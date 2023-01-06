package app.vazovsky.healsted.data.preferences.base

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_USER_INDEPENDENT_FILE_NAME = "pref_user_independent_file"

/** Преференсы, которые не очищаются при выходе из аккаунта */
@Singleton
class UserIndependentPreferenceStorage @Inject constructor(
    @ApplicationContext context: Context,
) : BasePreferencesStorage(context) {
    override fun createPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_USER_INDEPENDENT_FILE_NAME, Context.MODE_PRIVATE)
    }
}