package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.MonitoringAttribute

interface HealthRepository {
    fun getHealthMonitoring(): List<MonitoringAttribute>
}