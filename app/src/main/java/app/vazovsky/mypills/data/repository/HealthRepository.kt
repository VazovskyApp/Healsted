package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.MonitoringAttribute

interface HealthRepository {
    fun getHealthMonitoring(): List<MonitoringAttribute>
}