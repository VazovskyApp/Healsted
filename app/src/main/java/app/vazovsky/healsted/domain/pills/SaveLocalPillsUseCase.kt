package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import timber.log.Timber

/** Сохранение таблеток в локальную базу данных */
class SaveLocalPillsUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val pillMapper: PillMapper,
) : UseCaseUnary<SaveLocalPillsUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            Timber.d("UPDATE RESULT LIST: " + params.pills)
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
        val pills: List<Pill>,
    )
}