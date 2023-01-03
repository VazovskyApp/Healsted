package app.vazovsky.healsted.extensions

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import app.vazovsky.healsted.presentation.base.BaseActivity
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.NavigationCommand

fun Fragment.observeNavigationCommands(viewModel: BaseViewModel) {
    viewModel.navigationLiveEvent.observe(viewLifecycleOwner) { navigationCommand ->
        processNavCommand(
            findNavController(),
            navigationCommand,
            ::startActivity,
        )
    }
}

fun BaseActivity.observeNavigationCommands(viewModel: BaseViewModel, @IdRes containerId: Int) {
    viewModel.navigationLiveEvent.observe(this) { navigationCommand ->
        processNavCommand(
            findNavController(containerId),
            navigationCommand,
            ::startActivity,
        )
    }
}

private fun processNavCommand(
    navController: NavController,
    navigationCommand: NavigationCommand,
    startActivity: (Intent) -> Unit,
) {
    when (navigationCommand) {
        is NavigationCommand.To -> navController.navigateSafe(
            navigationCommand.directions,
            navigationCommand.navOptions,
        )

        is NavigationCommand.Back -> navController.popBackStack()
        is NavigationCommand.DeepLink -> navController.navigateSafe(
            navigationCommand.navDeepLinkRequest,
            navigationCommand.navOptions,
        )

        is NavigationCommand.Activity -> startActivity(navigationCommand.intent)
    }
}