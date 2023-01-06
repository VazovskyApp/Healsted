package app.vazovsky.healsted.data.preferences.base

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_FILE_NAME = "pref_file"

/**
 * Обычный файл префсов
 */
@Singleton
class RegularPreferenceStorage @Inject constructor(
    @ApplicationContext context: Context,
) : BasePreferencesStorage(context) {

    override fun createPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}