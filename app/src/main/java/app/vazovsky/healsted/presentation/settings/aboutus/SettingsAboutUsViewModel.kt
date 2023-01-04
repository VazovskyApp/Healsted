package app.vazovsky.healsted.presentation.settings.aboutus

import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class SettingsAboutUsViewModel @Inject constructor(
    private val destinations: SettingsAboutUsDestinations,
) : BaseViewModel() {

    /** Поделиться приложением */
    fun shareUrl() {
        navigate(destinations.shareUrl())
    }

    /** Открыть телеграм канал */
    fun openTelegramChannel() {
        navigate(destinations.telegramChannel())
    }

    /** Открыть чат с разработчиком */
    fun openTelegramVazovsky() {
        Timber.d("LOL VAZOVSKY")
        navigate(destinations.telegramVazovsky())
    }
}