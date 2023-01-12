package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.room.AppDatabase
import app.vazovsky.healsted.data.room.entity.PillEntity
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : RoomRepository {

    override fun getAllPills() = appDatabase.getPillsDao().getAllPills()

    override suspend fun getPillById(id: String) = appDatabase.getPillsDao().getPillById(id)

    override suspend fun insertPill(pill: PillEntity) = appDatabase.getPillsDao().insertPill(pill)

    override suspend fun deletePill(pill: PillEntity) = appDatabase.getPillsDao().deletePill(pill)
}