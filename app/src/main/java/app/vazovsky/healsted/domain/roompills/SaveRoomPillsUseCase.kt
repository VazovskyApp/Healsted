package app.vazovsky.healsted.domain.roompills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Сохранение лекарств в Room */
class SaveRoomPillsUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val roomRepository: RoomRepository,
) : UseCaseUnary<SaveRoomPillsUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            params.pills.forEach { pill ->
                roomRepository.insertPill(pillMapper.fromModelToEntity(pill))
            }
            true
        } catch (e: Exception) {
            false
        }
        return isSuccess
    }

    data class Params(
        /** Сохраняемый список лекарств */
        val pills: List<Pill>,
    )
}