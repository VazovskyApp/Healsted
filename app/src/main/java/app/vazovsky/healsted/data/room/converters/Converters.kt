package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class ConverterList<T> {

    @TypeConverter
    fun mapListToString(value: List<T>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<T>? {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(value, type)
    }
}

abstract class Converter<T> {
    @TypeConverter
    fun fromTimestamp(data: T?): String {
        val type = object : TypeToken<T>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toTimestamp(data: String): T? {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(data, type)
    }
}
