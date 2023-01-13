package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TimesMapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String?, Boolean?>? {
        val mapType: Type = object : TypeToken<Map<String?, Boolean?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    open fun fromStringMap(map: Map<String?, Boolean?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }
}