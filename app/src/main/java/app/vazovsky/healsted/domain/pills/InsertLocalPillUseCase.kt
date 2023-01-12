package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import timber.log.Timber

/** Добавить лекарство в локальную базу данных */
class InsertLocalPillUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val pillMapper: PillMapper,
) : UseCaseUnary<InsertLocalPillUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            roomRepository.insertPill(pillMapper.fromModelToEntity(params.pill))
            true
        } catch (e: Exception) {
            false
        }
        return isSuccess
    }

    data class Params(
        val pill: Pill,
    )
}