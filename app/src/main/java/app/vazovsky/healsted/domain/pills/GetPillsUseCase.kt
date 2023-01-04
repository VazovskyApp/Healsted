package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение данных о лекарствах */
class GetPillsUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<GetPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        return pillsRepository.getPills(params.slot)
    }

    data class Params(
        /** Отмеченный слот */
        val slot: PillsTabSlot?,
    )
}