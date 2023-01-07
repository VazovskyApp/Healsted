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
        viewModel.getProfile()
        viewModel.getLoyalty()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        profileLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    bindProfile(it)
                }
                task.addOnFailureListener {
                    Timber.d(it.localizedMessage)
                }
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
        loyaltyLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    bindLoyalty(it)
                }
                task.addOnFailureListener {
                    Timber.d(it.localizedMessage)
                }
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        textViewLevel.setOnClickListener {
            //TODO сделать открытие BottomSheet с инфой об уровнях
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        constraintLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun bindProfile(it: DocumentSnapshot?) = with(binding) {
        val profile = it?.toObject(Account::class.java)
        profile?.let { account ->
            textViewNickname.text = account.nickname
        }
    }

    private fun bindLoyalty(it: DocumentSnapshot?) = with(binding) {
        // TODO сделать так, чтоб все превращалось в объекты уже в use case
        val loyalty = it?.toObject(LoyaltyProgress::class.java)
        loyalty?.let { loyaltyProgress ->
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
}