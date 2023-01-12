package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.SettingsItem

interface SettingsRepository {

    //<editor-fold desc="Settings">
    /** Получение настроек */
    fun getSettings(): List<SettingsItem>
    //</editor-fold>

}