package app.vazovsky.mypills.domain.pills

import app.vazovsky.mypills.data.model.PillsTab
import app.vazovsky.mypills.data.repository.PillsRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение табов */
class GetTabsUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<UseCase.None, List<PillsTab>>() {
    override suspend fun execute(params: UseCase.None): List<PillsTab> {
        return pillsRepository.getTabs()
    }
}