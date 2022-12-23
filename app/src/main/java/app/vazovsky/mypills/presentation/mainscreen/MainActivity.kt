package app.vazovsky.mypills.presentation.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.ActivityMainBinding
import app.vazovsky.mypills.managers.BottomNavigationViewManager
import app.vazovsky.mypills.presentation.base.BaseActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomNavigationViewManager {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    override fun onBindViewModel() = Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
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