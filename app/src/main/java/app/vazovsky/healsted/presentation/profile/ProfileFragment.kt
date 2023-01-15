package app.vazovsky.healsted.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.databinding.FragmentProfileBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/** Экран с информацией об аккаунте */
@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun callOperations() {
        viewModel.getProfileSnapshot()
        viewModel.getLoyaltySnapshot()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        profileSnapshotLiveData.observe { result ->
            result.doOnSuccess { setProfileSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        profileLiveData.observe { result ->
            result.doOnSuccess { account -> bindProfile(account) }
            result.doOnFailure { Timber.d(it.message) }
        }
        loyaltySnapshotLiveData.observe { result ->
            result.doOnSuccess { setLoyaltySnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        loyaltyLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { loyalty -> bindLoyalty(loyalty) }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getProfileSnapshot() }

        textViewLevel.setOnClickListener {
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun bindProfile(account: Account) = with(binding) {
        textViewNickname.text = account.nickname
    }

    private fun bindLoyalty(loyaltyProgress: LoyaltyProgress) = with(binding) {
        textViewLevel.text = loyaltyProgress.level.toString()

        val currentProgress = loyaltyProgress.currentValue
        val maxProgress = loyaltyProgress.level.xpCount

        textViewAccountProgress.text = buildString {
            append(currentProgress)
            append(" / ")
            append(maxProgress)
        }

        progressBarLevel.progress = currentProgress
        progressBarLevel.max = maxProgress
    }

    private fun setLoyaltySnapshotTask(task: Task<DocumentSnapshot>) {
        task.addOnSuccessListener { viewModel.getLoyalty(it) }
        task.addOnFailureListener { Timber.d(it.message) }
    }

    private fun setProfileSnapshotTask(task: Task<DocumentSnapshot>) {
        task.addOnSuccessListener { viewModel.getProfile(it) }
        task.addOnFailureListener { Timber.d(it.message) }
    }
}