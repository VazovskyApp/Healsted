package app.vazovsky.healsted.presentation.settings.notification

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsNotificationViewModel @Inject constructor(
    private val destinations: SettingsNotificationDestinations,
) : BaseViewModel() {
    fun openAppSettings() {
        navigate(destinations.appSettings())
    }
}