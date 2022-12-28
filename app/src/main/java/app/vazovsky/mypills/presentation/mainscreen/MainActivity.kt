package app.vazovsky.mypills.presentation.mainscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import app.vazovsky.mypills.R
import app.vazovsky.mypills.core.core.NotificationCore
import app.vazovsky.mypills.core.core.NotificationCore.Companion.NOTIFICATION_CLICK_ENDPOINT
import app.vazovsky.mypills.core.core.NotificationCore.Companion.NOTIFICATION_EXTRA
import app.vazovsky.mypills.core.core.NotificationCore.Companion.NOTIFICATION_ID
import app.vazovsky.mypills.databinding.ActivityMainBinding
import app.vazovsky.mypills.extensions.observeNavigationCommands
import app.vazovsky.mypills.managers.BottomNavigationViewManager
import app.vazovsky.mypills.presentation.base.BaseActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

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

        notificationCore.createWorker(
            application,
            token = getToken(),
            endPoint = "MyPills",
            deviceId = "test",
            notificationImage = R.drawable.ic_menu_health,
            notificationPackageName = "app.vazovsky.mypills",
            notificationClassPackageName = "app.vazovsky.mypills.MainActivity"
        )

        notificationCore.sendOnDefaultChannel(
            context = applicationContext,
            notificationId = "0",
            notificationImage = R.drawable.ic_menu_health,
            data = null,
            notificationTitle = "Так, а тут вроде с воркером",
            notificationContent = "Ура, я продвигаюсь чуток",
            notificationPackageName = packageName,
            notificationClassPackageName = MainActivity::class.java.toString(),
            clickReferrerEndPoint = "MyPills",
        )

        setupBottomNavigation()
    }

    private fun getToken(): String {
        return "**"
    }

    override fun onResume() {
        super.onResume()
        checkIntent(intent)
    }

    private fun checkIntent(intent: Intent?) {
        Timber.d("LOL check intent")
        intent?.let {
            if (it.hasExtra(NOTIFICATION_EXTRA)) {
                val endPoint = it.getStringExtra(NOTIFICATION_CLICK_ENDPOINT)
                val id = it.getStringExtra(NOTIFICATION_ID)

                if (endPoint != null && id != null) {
                    notificationCore.clickedOnNotification(
                        endPoint = endPoint,
                        token = getToken(),
                        id = id
                    )
                }
            }
        }
    }

    override fun setNavigationViewVisibility(isVisible: Boolean) {
        binding.bottomNavigationViewContainer.isVisible = isVisible
    }

    override fun getNavigationView() = binding.bottomNavigationViewContainer

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

    private fun getNavHostFragment(): NavHostFragment {
        return (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
    }


}