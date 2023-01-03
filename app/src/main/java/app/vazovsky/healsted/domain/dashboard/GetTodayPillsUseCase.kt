package app.vazovsky.healsted.domain.dashboard

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.DashboardRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение медикаментов, назначенных на сегодняшний день */
class GetTodayPillsUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) : UseCaseUnary<UseCase.None, List<Pill>>() {
    override suspend fun execute(params: UseCase.None): List<Pill> {
        return dashboardRepository.getTodayPills()
    }
}