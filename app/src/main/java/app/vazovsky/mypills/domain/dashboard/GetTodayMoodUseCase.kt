package app.vazovsky.mypills.domain.dashboard

import app.vazovsky.mypills.data.model.Mood
import app.vazovsky.mypills.data.repository.DashboardRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение сегодняшнего настроения */
class GetTodayMoodUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) : UseCaseUnary<UseCase.None, Mood>() {
    override suspend fun execute(params: UseCase.None): Mood {
        return dashboardRepository.getTodayMood()
    }
}