package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.repository.HealthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение данных о мониторинге здоровья */
class GetHealthMonitoringUseCase @Inject constructor(
    private val healthRepository: HealthRepository,
) : UseCaseUnary<UseCase.None, List<MonitoringAttribute>>() {
    override suspend fun execute(params: UseCase.None): List<MonitoringAttribute> {
        return healthRepository.getHealthMonitoring()
    }
}