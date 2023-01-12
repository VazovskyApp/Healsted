package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import timber.log.Timber

/** Добавить лекарство в локальную базу данных */
class InsertPillToDatabaseUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) : UseCaseUnary<InsertPillToDatabaseUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            roomRepository.insertPill(params.pill)
            true
        } catch (e: Exception) {
            Timber.d("LOL exc: ${e.message}")
            false
        }
        Timber.d("LOL insert: $isSuccess")
        return isSuccess
    }

    data class Params(
        val pill: Pill,
    )
}