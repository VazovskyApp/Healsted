package app.vazovsky.healsted.data.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/** Базовые преференсы */
abstract class BasePreferencesStorage constructor(
    protected val context: Context,
) {

    private val pref: SharedPreferences by lazy { createPreferences() }

    protected abstract fun createPreferences(): SharedPreferences

    fun getString(key: String?, defValue: String? = null): String? = pref.getString(key, defValue)
    fun getInt(key: String?, defValue: Int = 0): Int = pref.getInt(key, defValue)
    fun getLong(key: String?, defValue: Long = 0L): Long = pref.getLong(key, defValue)
    fun getFloat(key: String?, defValue: Float = 0.0f): Float = pref.getFloat(key, defValue)
    fun getBoolean(key: String?, defValue: Boolean = false): Boolean = pref.getBoolean(key, defValue)

    fun edit(): SharedPreferences.Editor = pref.edit()

    @SuppressLint("ApplySharedPref")
    @WorkerThread
    suspend fun clear(commitNow: Boolean = false) = suspendCoroutine {
        val editor = pref.edit().clear()
        if (commitNow) editor.commit()
        else editor.apply()
        it.resume(Unit)
    }
}