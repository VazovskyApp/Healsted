package app.vazovsky.mypills.presentation.dashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.WorkManager
import app.vazovsky.mypills.R
import app.vazovsky.mypills.databinding.FragmentDashboardBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import app.vazovsky.mypills.presentation.mainscreen.MainActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint


const val CHANNEL_ID = "my pills"

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}