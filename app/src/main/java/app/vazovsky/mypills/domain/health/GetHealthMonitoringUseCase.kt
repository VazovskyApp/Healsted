package app.vazovsky.mypills.domain.health

import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.data.repository.HealthRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение данных о мониторинге здоровья */
class GetHealthMonitoringUseCase @Inject constructor(
    private val healthRepository: HealthRepository,
) : UseCaseUnary<UseCase.None, List<MonitoringAttribute>>() {
    override suspend fun execute(params: UseCase.None): List<MonitoringAttribute> {
        return healthRepository.getHealthMonitoring()
    }
}