package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.room.entity.PillEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getAllPills(): Flow<List<PillEntity>>
    suspend fun getPillById(id: String): PillEntity
    suspend fun insertPill(pill: PillEntity)
    suspend fun deletePill(pill: PillEntity)
}