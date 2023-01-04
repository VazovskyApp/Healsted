package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.repository.HealthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение истории мониторинга атрибута по типу */
class GetHealthMonitoringHistoryUseCase @Inject constructor(
    private val healthRepository: HealthRepository,
) : UseCaseUnary<GetHealthMonitoringHistoryUseCase.Params, List<MonitoringAttribute>>() {

    override suspend fun execute(params: Params): List<MonitoringAttribute> {
        return healthRepository.getHealthMonitoringHistory(params.type)
    }

    data class Params(
        val type: MonitoringType,
    )
}