package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import javax.inject.Inject

class HealthRepositoryImpl @Inject constructor() : HealthRepository {
    override fun getHealthMonitoring(): List<MonitoringAttribute> {
        return listOf(
            MonitoringAttribute(
                value = "45",
                type = MonitoringType.WEIGHT,
            ),
            MonitoringAttribute(
                value = "150",
                type = MonitoringType.HEIGHT,
            ),
            MonitoringAttribute(
                value = "36.6",
                type = MonitoringType.TEMPERATURE,
            ),
            MonitoringAttribute(
                value = "120/80",
                type = MonitoringType.BLOOD_PRESSURE,
            ),
        )
    }
}