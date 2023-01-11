package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import com.google.firebase.Timestamp
import javax.inject.Inject

class HealthRepositoryImpl @Inject constructor() : HealthRepository {

    private val listOfHealthMonitoring = listOf(
        MonitoringAttribute(
            value = "45",
            type = MonitoringType.WEIGHT,
            date = Timestamp.now(),
        ),
        MonitoringAttribute(
            value = "150",
            type = MonitoringType.HEIGHT,
            date = Timestamp.now(),
        ),
        MonitoringAttribute(
            value = "36.6",
            type = MonitoringType.TEMPERATURE,
            date = Timestamp.now(),
        ),
        MonitoringAttribute(
            value = "120/80",
            type = MonitoringType.BLOOD_PRESSURE,
            date = Timestamp.now(),
        ),
    )

    override fun getHealthMonitoringHistory(type: MonitoringType): List<MonitoringAttribute> {
        return listOfHealthMonitoring.filter { it.type == type }
    }
}