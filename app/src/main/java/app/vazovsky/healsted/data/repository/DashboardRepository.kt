package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill

interface DashboardRepository {
    fun getTodayPills(): List<Pill>
    fun getTodayMood(): Mood
}