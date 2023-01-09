package app.vazovsky.healsted.presentation.profile.profile

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
            result.doOnSuccess { task ->
                task.addOnSuccessListener { snapshot ->
                    viewModel.getProfile(snapshot)
                    Timber.d("PROFILE SNAPSHOT: $snapshot")
                }
            }
        }
        profileLiveData.observe { result ->
            result.doOnSuccess { account ->
                bindProfile(account)
                Timber.d("PROFILE: $account")
            }
        }
        loyaltySnapshotLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { task ->
                task.addOnSuccessListener { snapshot ->
                    viewModel.getLoyalty(snapshot)
                    Timber.d("LOYALTY SNAPSHOT: $snapshot")
                }
            }
        }
        loyaltyLiveData.observe { result ->
            result.doOnSuccess { loyalty ->
                bindLoyalty(loyalty)
                Timber.d("LOYALTY: $loyalty")
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getProfileSnapshot() }

        textViewLevel.setOnClickListener {
            //TODO сделать открытие BottomSheet с инфой об уровнях
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        constraintLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun bindProfile(account: Account) = with(binding) {
        textViewNickname.text = account.nickname
    }

    private fun bindLoyalty(loyaltyProgress: LoyaltyProgress) = with(binding) {
        textViewLevel.text = loyaltyProgress.level.toString()

        textViewAccountProgress.text = buildString {
            append(loyaltyProgress.currentValue)
            append(" / ")
            append(loyaltyProgress.level.xpCount)
        }

        progressIndicatorAccountProgress.progress = loyaltyProgress.currentValue
        progressIndicatorAccountProgress.max = loyaltyProgress.level.xpCount
    }
}