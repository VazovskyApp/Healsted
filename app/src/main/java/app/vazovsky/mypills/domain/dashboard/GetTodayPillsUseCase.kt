package app.vazovsky.mypills.domain.dashboard

import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.repository.DashboardRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение медикаментов, назначенных на сегодняшний день */
class GetTodayPillsUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) : UseCaseUnary<UseCase.None, List<Pill>>() {
    override suspend fun execute(params: UseCase.None): List<Pill> {
        return dashboardRepository.getTodayPills()
    }
}