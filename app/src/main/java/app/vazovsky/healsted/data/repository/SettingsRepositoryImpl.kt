package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.SettingType
import app.vazovsky.healsted.data.model.SettingsGroup
import app.vazovsky.healsted.data.model.SettingsItem
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override fun getSettings(): List<SettingsItem> {
        return listOf(
            SettingsItem(
                type = SettingType.ACCOUNT,
                icon = R.drawable.ic_settings_account,
                group = SettingsGroup.GENERAL,
            ),
            SettingsItem(
                type = SettingType.NOTIFICATION,
                icon = R.drawable.ic_settings_notifications,
                group = SettingsGroup.NOTIFICATION,
            ),
            SettingsItem(
                type = SettingType.SEND_FEEDBACK,
                icon = R.drawable.ic_settings_send_feedback,
                group = SettingsGroup.FEEDBACK,
            ),
            SettingsItem(
                type = SettingType.REPORT_A_BAG,
                icon = R.drawable.ic_settings_report_bug,
                group = SettingsGroup.FEEDBACK,
            ),
            SettingsItem(
                type = SettingType.FEATURES,
                icon = R.drawable.ic_settings_features,
                group = SettingsGroup.GENERAL,
            ),
            SettingsItem(
                type = SettingType.ABOUT_US,
                icon = R.drawable.ic_settings_about_us,
                group = SettingsGroup.GENERAL,
            ),
            SettingsItem(
                type = SettingType.LOG_OUT,
                icon = R.drawable.ic_settings_logout,
                group = SettingsGroup.GENERAL,
            ),
        )
    }
}