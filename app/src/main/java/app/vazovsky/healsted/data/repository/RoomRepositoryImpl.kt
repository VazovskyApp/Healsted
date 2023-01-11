package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.room.AppDatabase
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : RoomRepository {

    override fun getAllPills() = appDatabase.getPillsDao().getAllPills()

    override suspend fun getPillById(id: String) = appDatabase.getPillsDao().getPillById(id)

    override suspend fun insertPill(pill: Pill) = appDatabase.getPillsDao().insertPill(pill)

    override suspend fun deletePill(pill: Pill) = appDatabase.getPillsDao().deletePill(pill)
}