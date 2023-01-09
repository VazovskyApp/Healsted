package app.vazovsky.healsted.presentation.settings

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class SettingsDestinations @Inject constructor() {
    fun auth() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToAuthGraph(),
    )

    fun account() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsAccountFragment()
    )

    fun aboutUs() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsAboutUsFragment()
    )

    fun notification() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsNotificationFragment()
    )

    fun features() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsFeaturesFragment()
    )

    fun reportBug() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsReportBugFragment()
    )

    fun sendFeedback() = NavigationCommand.To(
        SettingsFragmentDirections.actionSettingsFragmentToSettingsSendFeedbackFragment()
    )
}