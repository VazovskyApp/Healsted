package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class ConverterArrayList<T> {
    @TypeConverter
    fun mapListToString(value: ArrayList<T>?): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<T>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): ArrayList<T>? {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<T>>() {}.type
        return gson.fromJson(value, type)
    }
}