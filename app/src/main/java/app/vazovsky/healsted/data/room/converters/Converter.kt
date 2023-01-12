package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class Converter<T> {
    @TypeConverter
    fun fromData(data: T?): String {
        val type = object : TypeToken<T>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toData(data: String): T? {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(data, type)
    }
}
