package app.vazovsky.mypills.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import timber.log.Timber

fun NavController.navigateSafe(direction: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.getAction(direction.actionId)?.let { navigate(direction, navOptions) }
}

fun NavController.navigateSafe(navDeepLinkRequest: NavDeepLinkRequest, navOptions: NavOptions? = null) {
    try {
        currentDestination?.let { navigate(navDeepLinkRequest, navOptions) }
    } catch (ex: IllegalStateException) {
        Timber.e(ex)
    }
}