package app.vazovsky.healsted.data.appinfo.repository

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.io.File
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class ResourcesRepository @Inject constructor(
    private val context: Context,
) {

    fun getLanguage(): String = Locale.getDefault().language

    fun getTimeZone(): String = TimeZone.getDefault().id

    fun getString(@StringRes id: Int): String = context.getString(id)

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String = context.getString(id, *formatArgs)

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(
            id,
            quantity,
            *formatArgs,
        )
    }

    fun getInteger(@IntegerRes id: Int): Int = context.resources.getInteger(id)

    fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)

    fun getDimensionPixelSize(resId: Int): Int = context.resources.getDimensionPixelSize(resId)

    fun getCacheDir(): File = context.cacheDir
}
