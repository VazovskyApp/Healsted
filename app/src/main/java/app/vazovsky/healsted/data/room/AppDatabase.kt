package app.vazovsky.healsted.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.vazovsky.healsted.data.room.AppDatabase.Companion.DATABASE_VERSION
import app.vazovsky.healsted.data.room.converters.DatesTakenSelectedListConverter
import app.vazovsky.healsted.data.room.converters.StringListConverter
import app.vazovsky.healsted.data.room.dao.PillDao
import app.vazovsky.healsted.data.room.entity.PillEntity

@TypeConverters(
    StringListConverter::class,
    DatesTakenSelectedListConverter::class,
)
@Database(
    entities = [
        PillEntity::class,
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