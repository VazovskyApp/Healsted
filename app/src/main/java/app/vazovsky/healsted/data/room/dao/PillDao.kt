package app.vazovsky.healsted.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.vazovsky.healsted.data.room.entity.PillEntity
import kotlinx.coroutines.flow.Flow

/** Dao объект с возможными функциями */
@Dao
interface PillDao {
    companion object {
        const val PILL_TABLE_NAME = "pills"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_TAKE_PILL_TYPE = "takePillType"
        const val COLUMN_TIMES = "times"
        const val COLUMN_DATES_TAKEN = "datesTaken"
        const val COLUMN_DATES_TAKEN_SELECTED = "datesTakenSelected"
        const val COLUMN_START_DATE = "startDate"
        const val COLUMN_END_DATE = "endDate"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_COMMENT = "comment"
    }

    /** Получение всех лекарств из Room */
    @Query("SELECT * FROM $PILL_TABLE_NAME")
    fun getAllPills(): Flow<List<PillEntity>>

    /** Добавление лекарства в Room */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPill(pill: PillEntity)

    /** Получение лекарства по ID из Room */
    @Query("SELECT * FROM $PILL_TABLE_NAME WHERE $COLUMN_ID=(:id)")
    suspend fun getPillById(id: String): PillEntity

    /** Удаление лекарства из Room */
    @Delete()
    suspend fun deletePill(pill: PillEntity)
}