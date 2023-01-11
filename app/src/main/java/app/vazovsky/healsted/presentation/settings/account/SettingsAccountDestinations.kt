package app.vazovsky.healsted.presentation.settings.account

import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class SettingsAccountDestinations @Inject constructor() {
    fun auth() = NavigationCommand.To(
        SettingsAccountFragmentDirections.actionSettingsAccountFragmentToAuthGraph()
    )
}