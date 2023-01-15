package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.SettingType
import app.vazovsky.healsted.data.model.SettingsItem
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    //<editor-fold desc="Settings">
    /** Получение настроек */
    override fun getSettings(): List<SettingsItem> {
        return listOf(
            SettingsItem(
                type = SettingType.ACCOUNT,
                icon = R.drawable.ic_settings_account,
            ),
            SettingsItem(
                type = SettingType.NOTIFICATION,
                icon = R.drawable.ic_settings_notifications,
            ),
            SettingsItem(
                type = SettingType.ABOUT_US,
                icon = R.drawable.ic_settings_about_us,
            ),
            SettingsItem(
                type = SettingType.LOG_OUT,
                icon = R.drawable.ic_settings_logout,
            ),
        )
    }
    //</editor-fold>

}