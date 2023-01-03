package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.SettingsItem

interface SettingsRepository {
    fun getSettings(): List<SettingsItem>
}