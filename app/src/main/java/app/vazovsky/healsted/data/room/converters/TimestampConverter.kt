package app.vazovsky.healsted.data.room.converters

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import com.google.gson.Gson

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(data: Timestamp): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun toTimestamp(data: String): Timestamp {
        return Gson().fromJson(data, Timestamp::class.java)
    }
}