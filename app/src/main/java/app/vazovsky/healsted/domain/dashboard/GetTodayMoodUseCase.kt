package app.vazovsky.healsted.domain.dashboard

import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.repository.DashboardRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение настроения за сегодня */
class GetTodayMoodUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) : UseCaseUnary<UseCase.None, Mood>() {
    override suspend fun execute(params: UseCase.None): Mood {
        return dashboardRepository.getTodayMood()
    }
}