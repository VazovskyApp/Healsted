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
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_CLICK_ENDPOINT
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_EXTRA
import app.vazovsky.healsted.core.core.NotificationCore.Companion.NOTIFICATION_ID
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ActivityMainBinding
import app.vazovsky.healsted.extensions.observeNavigationCommands
import app.vazovsky.healsted.extensions.withZeroSecondsAndNano
import app.vazovsky.healsted.managers.BottomNavigationViewManager
import app.vazovsky.healsted.presentation.base.BaseActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
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
        val nowTime = LocalTime.now().withZeroSecondsAndNano()
        val nowDate = LocalDate.now()
        val pill = Pill(
            name = "Доза кокаина",
            times = mapOf(
                UUID.randomUUID().toString() to nowTime.minusMinutes(1),
                UUID.randomUUID().toString() to nowTime,
                UUID.randomUUID().toString() to nowTime.plusMinutes(1),
                UUID.randomUUID().toString() to nowTime.plusMinutes(2),
            ),
            startDate = nowDate,
            endDate = nowDate.plusDays(2),
            datesTaken = DatesTakenType.SELECTED_DAYS,
            datesTakenSelected = arrayListOf(6),
        )

        notificationCore.createWorker(
            application,
            token = getToken(),
            endPoint = "Healsted",
            deviceId = "test",
            notificationImage = R.drawable.ic_logo_red,
            notificationPackageName = "app.vazovsky.healsted",
            notificationClassPackageName = "app.vazovsky.healsted.MainActivity",
            uid = "5JJpee0Ur5bhOb3Bit2yuuqQoUl1",
            pill = pill,
        )

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

    private fun getToken(): String {
        return "**"
    }

    private fun checkIntent(intent: Intent?) {
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