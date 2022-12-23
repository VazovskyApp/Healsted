package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.Mood
import app.vazovsky.mypills.data.model.Pill

interface DashboardRepository {
    fun getTodayPills(): List<Pill>
    fun getTodayMood(): Mood
}