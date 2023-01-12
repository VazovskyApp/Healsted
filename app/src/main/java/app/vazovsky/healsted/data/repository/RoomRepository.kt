package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.room.entity.PillEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    //<editor-fold desc="Pills">
    /** Получение всех лекарств из Room */
    fun getAllPills(): Flow<List<PillEntity>>

    /** Получение лекарства по ID из Room */
    suspend fun getPillById(id: String): PillEntity

    /** Добавление лекарства в Room */
    suspend fun insertPill(pill: PillEntity)

    /** Удаление лекарства из Room */
    suspend fun deletePill(pill: PillEntity)
    //</editor-fold>

}