package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Pill
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getAllPills(): Flow<List<Pill>>
    suspend fun getPillById(id: String): Pill
    suspend fun insertPill(pill: Pill)
    suspend fun deletePill(pill: Pill)
}