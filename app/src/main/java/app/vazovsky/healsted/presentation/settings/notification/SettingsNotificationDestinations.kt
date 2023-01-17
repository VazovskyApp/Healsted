package app.vazovsky.healsted.presentation.settings.notification

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import app.vazovsky.healsted.presentation.base.NavigationCommand
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsNotificationDestinations @Inject constructor(
    @ApplicationContext val context: Context,
) {

    fun appSettings(): NavigationCommand {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        return NavigationCommand.Activity(intent)
    }
}