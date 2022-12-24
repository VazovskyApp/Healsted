package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.data.model.MonitoringType
import javax.inject.Inject

class HealthRepositoryImpl @Inject constructor() : HealthRepository {
    override fun getHealthMonitoring(): List<MonitoringAttribute> {
        return listOf(
            MonitoringAttribute(
                id = "0",
                title = "Вес",
                value = "45",
                type = MonitoringType.WEIGHT,
            ),
            MonitoringAttribute(
                id = "0",
                title = "Рост",
                value = "150",
                type = MonitoringType.HEIGHT,
            ),
            MonitoringAttribute(
                id = "0",
                title = "Температура",
                value = "36.6",
                type = MonitoringType.TEMPERATURE,
            ),
            MonitoringAttribute(
                id = "0",
                title = "Давление",
                value = "120/80",
                type = MonitoringType.BLOOD_PRESSURE,
            ),
        )
    }
}