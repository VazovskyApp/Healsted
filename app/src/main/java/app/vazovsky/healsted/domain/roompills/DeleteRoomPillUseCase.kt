package app.vazovsky.healsted.domain.roompills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Удаление лекарства из Room */
class DeleteRoomPillUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val roomRepository: RoomRepository,
) : UseCaseUnary<DeleteRoomPillUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            roomRepository.deletePill(pillMapper.fromModelToEntity(params.pill))
            true
        } catch (e: Exception) {
            false
        }
        return isSuccess
    }

    data class Params(
        /** Удаляемое лекарство */
        val pill: Pill,
    )
}