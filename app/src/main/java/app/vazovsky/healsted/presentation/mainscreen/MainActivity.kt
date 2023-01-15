package app.vazovsky.healsted.presentation.mainscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.core.core.NotificationCore.Companion.DEFAULT_TOKEN
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_CLICK_ENDPOINT
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_EXTRA
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_ID
import app.vazovsky.healsted.databinding.ActivityMainBinding
import app.vazovsky.healsted.extensions.observeNavigationCommands
import app.vazovsky.healsted.managers.BottomNavigationViewManager
import app.vazovsky.healsted.presentation.base.BaseActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomNavigationViewManager {

    @Inject lateinit var notificationCore: NotificationCore

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationCore.init(application = application, owner = this)

        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        checkIntent(intent)
    }

    override fun setNavigationViewVisibility(isVisible: Boolean) {
        binding.bottomNavigationViewContainer.isVisible = isVisible
    }

    override fun getNavigationView() = binding.bottomNavigationViewContainer

    private fun checkIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(NOTIFICATION_EXTRA)) {
                val endPoint = it.getStringExtra(NOTIFICATION_CLICK_ENDPOINT)
                val id = it.getStringExtra(NOTIFICATION_ID)

                if (endPoint != null && id != null) {
                    notificationCore.clickedOnNotification(
                        endPoint = endPoint,
                        token = DEFAULT_TOKEN,
                        id = id
                    )
                }
            }
        }
    }

    /** Настройка нижнего меню навигации */
    private fun setupBottomNavigation() {
        binding.bottomNavigationView.apply {
            setupWithNavController(
                getNavHostFragment().navController.apply {
                    attachNavController(this)
                }
            )
        }
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (getNavHostFragment().navController.currentDestination?.parent == null) {
                true
            } else {
                NavigationUI.onNavDestinationSelected(item, getNavHostFragment().navController)
            }
        }
        binding.fabDashboard.setOnClickListener {
            binding.bottomNavigationView.selectedItemId = R.id.dashboard_graph
        }
    }

    /** Поиск контейнера для фрагментов */
    private fun getNavHostFragment(): NavHostFragment {
        return (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
    }
}