package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.room.AppDatabase
import app.vazovsky.healsted.data.room.entity.PillEntity
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : RoomRepository {

    //<editor-fold desc="Pills">
    /** Получение всех лекарств из Room */
    override fun getAllPills() = appDatabase.getPillsDao().getAllPills()

    /** Получение лекарства по ID из Room */
    override suspend fun getPillById(id: String) = appDatabase.getPillsDao().getPillById(id)

    /** Добавление лекарства в Room */
    override suspend fun insertPill(pill: PillEntity) = appDatabase.getPillsDao().insertPill(pill)

    /** Удаление лекарства из Room */
    override suspend fun deletePill(pill: PillEntity) = appDatabase.getPillsDao().deletePill(pill)
    //</editor-fold>

}