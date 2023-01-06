package app.vazovsky.healsted.data.preferences.base

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_NON_SECURE_FILE_NAME = "pref_non_secure_file"

/**
 * Шифрованные префсы без шифрования
 */
@Singleton
class SecuredSharedPreferences @Inject constructor(
    @ApplicationContext context: Context,
) : BasePreferencesStorage(context) {

    override fun createPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_NON_SECURE_FILE_NAME, Context.MODE_PRIVATE)
    }
}