package app.vazovsky.healsted.presentation.settings.aboutus

import android.content.Intent
import android.net.Uri
import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

private const val TELEGRAM_CHANNEL_URL = "tg://resolve?domain=healsted"
private const val TELEGRAM_VAZOVSKY_URL = "tg://resolve?domain=vazovsky_17"
private const val SHARE_URL = "https://www.t.me/healsted"
private const val DEFAULT_SHARE_TYPE = "text/plain"


class SettingsAboutUsDestinations @Inject constructor() {
    fun shareUrl() = NavigationCommand.Activity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, SHARE_URL)
                type = DEFAULT_SHARE_TYPE
            }, null
        )
    )

    fun telegramChannel() = NavigationCommand.Activity(
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(TELEGRAM_CHANNEL_URL)
        }
    )

    fun telegramVazovsky() = NavigationCommand.Activity(
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(TELEGRAM_VAZOVSKY_URL)
        }
    )
}