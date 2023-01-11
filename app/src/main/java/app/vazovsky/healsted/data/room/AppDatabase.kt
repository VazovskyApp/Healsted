package app.vazovsky.healsted.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.room.AppDatabase.Companion.DATABASE_VERSION
import app.vazovsky.healsted.data.room.converters.DatesTakenSelectedListConverter
import app.vazovsky.healsted.data.room.converters.TimestampConverter
import app.vazovsky.healsted.data.room.converters.TimestampListConverter
import app.vazovsky.healsted.data.room.dao.PillDao

@TypeConverters(
    TimestampConverter::class,
    TimestampListConverter::class,
    DatesTakenSelectedListConverter::class,
)
@Database(
    entities = [
        Pill::class,
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "healsted_db"
    }

    abstract fun getPillsDao(): PillDao
}