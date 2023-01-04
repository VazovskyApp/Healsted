package app.vazovsky.healsted.presentation.profile.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentProfileBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun callOperations() {
        viewModel.getProfile()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        profileLiveData.observe { result ->
            result.doOnSuccess { profile ->
                bindProfile(profile)
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

    private fun bindProfile(profile: Account) = with(binding) {
        textViewNickname.text = profile.nickname
        textViewLevel.text = profile.level.toString()
    }
}