package app.vazovsky.healsted.data.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_USER_INDEPENDENT_FILE_NAME = "pref_user_independent_file"

/** Преференсы, которые зависят от логаута */
@Singleton
class UserIndependentPreferenceStorage @Inject constructor(
    context: Context,
) : BasePreferencesStorage(context) {
    override fun createPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_USER_INDEPENDENT_FILE_NAME, Context.MODE_PRIVATE)
    }
}