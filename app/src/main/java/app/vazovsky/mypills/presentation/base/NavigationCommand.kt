package app.vazovsky.mypills.presentation.base

import android.content.Intent
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data class To(val directions: NavDirections, val navOptions: NavOptions? = null) : NavigationCommand()
    data class DeepLink(val navDeepLinkRequest: NavDeepLinkRequest, val navOptions: NavOptions? = null) : NavigationCommand()
    data class Activity(val intent: Intent) : NavigationCommand()
    object Back : NavigationCommand()
}