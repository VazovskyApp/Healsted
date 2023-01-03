package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение табов */
class GetTabsUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<UseCase.None, List<PillsTab>>() {
    override suspend fun execute(params: UseCase.None): List<PillsTab> {
        return pillsRepository.getTabs()
    }
}