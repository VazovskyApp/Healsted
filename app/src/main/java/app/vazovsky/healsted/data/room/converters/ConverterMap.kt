package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class ConverterMap<T, B> {

    @TypeConverter
    fun mapStringToMap(value: String?): Map<T, B>? {
        val gson = Gson()
        val type = object : TypeToken<Map<T, B>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun mapMapToString(map: Map<T, B>): String {
        val gson = Gson()
        val type = object : TypeToken<Map<T, B>>() {}.type
        return gson.toJson(map, type)
    }
}
