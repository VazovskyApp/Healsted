package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.data.room.entity.PillEntity
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetLocalPillsUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) : UseCaseUnary<UseCase.None, Flow<List<PillEntity>>>() {
    override suspend fun execute(params: UseCase.None): Flow<List<PillEntity>> {
        return roomRepository.getAllPills()
    }
}