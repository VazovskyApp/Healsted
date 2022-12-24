package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.SettingsItem

interface SettingsRepository {
    fun getSettings(): List<SettingsItem>
}