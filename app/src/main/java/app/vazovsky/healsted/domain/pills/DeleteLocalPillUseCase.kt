package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Удаление данных о лекарстве локально */
class DeleteLocalPillUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val pillMapper: PillMapper,
) : UseCaseUnary<DeleteLocalPillUseCase.Params, Boolean>() {
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
        val pill: Pill,
    )
}