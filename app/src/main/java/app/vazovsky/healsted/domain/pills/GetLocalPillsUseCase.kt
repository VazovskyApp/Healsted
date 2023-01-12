package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class GetLocalPillsUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) : UseCaseUnary<UseCase.None, List<Pill>>() {
    override suspend fun execute(params: UseCase.None): List<Pill> {
        Timber.d("LOL getLocalPillsUseCase")
        val pills = mutableListOf<Pill>()
        try {
            val newPills = roomRepository.getAllPills()
            //pills.addAll(newPills)
        } catch (e: Exception) {
            Timber.d(e.message)
        }
        Timber.d("LOL pills: $pills")
        return pills
    }
}