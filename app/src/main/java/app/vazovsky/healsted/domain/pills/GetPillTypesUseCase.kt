package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение типов лекарств с иконками */
class GetPillTypesUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<UseCase.None, List<PillTypeItem>>() {
    override suspend fun execute(params: UseCase.None): List<PillTypeItem> {
        return pillsRepository.getPillsTypes()
    }
}