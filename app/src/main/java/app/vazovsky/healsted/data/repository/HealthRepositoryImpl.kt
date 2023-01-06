package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import java.time.OffsetDateTime
import javax.inject.Inject

class HealthRepositoryImpl @Inject constructor() : HealthRepository {

    private val listOfHealthMonitoring = listOf(
        MonitoringAttribute(
            value = "45",
            type = MonitoringType.WEIGHT,
            date = OffsetDateTime.now(),
        ),
        MonitoringAttribute(
            value = "150",
            type = MonitoringType.HEIGHT,
            date = OffsetDateTime.now(),
        ),
        MonitoringAttribute(
            value = "36.6",
            type = MonitoringType.TEMPERATURE,
            date = OffsetDateTime.now(),
        ),
        MonitoringAttribute(
            value = "120/80",
            type = MonitoringType.BLOOD_PRESSURE,
            date = OffsetDateTime.now(),
        ),
    )

    override fun getHealthMonitoring(): List<MonitoringAttribute> {
        return listOfHealthMonitoring
    }

    override fun getHealthMonitoringHistory(type: MonitoringType): List<MonitoringAttribute> {
        return listOfHealthMonitoring.filter { it.type == type }
    }
}