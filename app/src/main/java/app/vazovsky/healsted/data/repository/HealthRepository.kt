package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType

interface HealthRepository {
    fun getHealthMonitoringHistory(type: MonitoringType): List<MonitoringAttribute>
}