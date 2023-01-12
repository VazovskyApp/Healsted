package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import timber.log.Timber

/** Сохранение таблеток в локальную базу данных */
class SavePillsToDatabaseUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) : UseCaseUnary<SavePillsToDatabaseUseCase.Params, Boolean>() {
    override suspend fun execute(params: Params): Boolean {
        val isSuccess = try {
            Timber.d("UPDATE RESULT LIST: " + params.pills)
            params.pills.forEach { pill ->
                roomRepository.insertPill(pill)
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