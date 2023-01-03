package app.vazovsky.mypills.domain.pills

import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillsTabSlot
import app.vazovsky.mypills.data.repository.PillsRepository
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

class GetPillsUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<GetPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        return pillsRepository.getPills(params.slot)
    }

    data class Params(
        val slot: PillsTabSlot?,
    )
}