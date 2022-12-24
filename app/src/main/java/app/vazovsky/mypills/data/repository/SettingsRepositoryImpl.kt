package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.SettingType
import app.vazovsky.mypills.data.model.SettingsGroup
import app.vazovsky.mypills.data.model.SettingsItem
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override fun getSettings(): List<SettingsItem> {
        return listOf(
            SettingsItem(
                id = "0",
                title = "Аккаунт",
                type = SettingType.ACCOUNT,
                icon = R.drawable.ic_settings_account,
                group = SettingsGroup.GENERAL,
            ),
            SettingsItem(
                id = "1",
                title = "Уведомления",
                type = SettingType.NOTIFICATION,
                icon = R.drawable.ic_settings_notifications,
                group = SettingsGroup.NOTIFICATION,
            ),
            SettingsItem(
                id = "2",
                title = "Написать разработчику",
                type = SettingType.SEND_FEEDBACK,
                icon = R.drawable.ic_settings_send_feedback,
                group = SettingsGroup.FEEDBACK,
            ),
            SettingsItem(
                id = "3",
                title = "Рассказать о баге",
                type = SettingType.REPORT_A_BAG,
                icon = R.drawable.ic_settings_report_bug,
                group = SettingsGroup.FEEDBACK,
            ),
            SettingsItem(
                id = "4",
                title = "О приложении",
                type = SettingType.ABOUT_US,
                icon = R.drawable.ic_settings_about_us,
                group = SettingsGroup.FEEDBACK,
            ),
            SettingsItem(
                id = "5",
                title = "Выйти",
                type = SettingType.LOG_OUT,
                icon = R.drawable.ic_settings_logout,
                group = SettingsGroup.GENERAL,
            ),
        )
    }
}