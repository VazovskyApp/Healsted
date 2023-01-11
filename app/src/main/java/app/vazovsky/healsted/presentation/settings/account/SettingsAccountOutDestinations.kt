package app.vazovsky.healsted.presentation.settings.account

import android.content.Context
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import app.vazovsky.healsted.R
import app.vazovsky.healsted.presentation.base.NavigationCommand
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsAccountOutDestinations @Inject constructor(
    @ApplicationContext val context: Context,
) {
    fun auth() = NavigationCommand.DeepLink(
        NavDeepLinkRequest.Builder
            .fromUri(
                context.getString(R.string.deep_link_auth_graph).toUri(),
            ).build(),
        NavOptions.Builder().setPopUpTo(destinationId = R.id.settings_graph, inclusive = true, saveState = false).build()
    )
}