package app.vazovsky.healsted.presentation.settings.aboutus

import android.content.Context
import android.content.Intent
import android.net.Uri
import app.vazovsky.healsted.R
import app.vazovsky.healsted.presentation.base.NavigationCommand
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val DEFAULT_SHARE_TYPE = "text/plain"

class SettingsAboutUsDestinations @Inject constructor(
    @ApplicationContext val context: Context,
) {
    fun shareUrl() = NavigationCommand.Activity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_link))
                type = DEFAULT_SHARE_TYPE
            }, null
        )
    )

    fun telegramChannel() = NavigationCommand.Activity(
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(context.getString(R.string.telegram_channel_link))
        }
    )

    fun telegramVazovsky() = NavigationCommand.Activity(
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(context.getString(R.string.telegram_vazovsky_link))
        }
    )
}