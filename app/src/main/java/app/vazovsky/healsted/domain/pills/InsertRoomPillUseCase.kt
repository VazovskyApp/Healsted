package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Добавить лекарство в Room */
class InsertRoomPillUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val roomRepository: RoomRepository,
) : UseCaseUnary<InsertRoomPillUseCase.Params, Boolean>() {
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
        /** Добавляемое лекарство */
        val pill: Pill,
    )
}