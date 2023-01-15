package app.vazovsky.healsted.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentProfileBinding
import app.vazovsky.healsted.extensions.addLinearSpaceItemDecoration
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.profile.adapter.PillsHistoryAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран с информацией об аккаунте */
@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    @Inject lateinit var pillsHistoryAdapter: PillsHistoryAdapter

    override fun callOperations() {
        viewModel.getProfileSnapshot()
        viewModel.getPillsHistorySnapshot()
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
        listPillsSnapshotLiveData.observe { result ->
            result.doOnSuccess { setPillsSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        listPillsHistoryLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { history -> pillsHistoryAdapter.submitList(history) }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?): Unit = with(binding) {
        root.fitTopInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getProfileSnapshot() }

        setupRecyclerView()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayout.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun bindProfile(account: Account) = with(binding.toolbar) {
        title = account.nickname
    }

    private fun setupRecyclerView() = with(binding.recyclerViewPillsHistory) {
        emptyView = binding.emptyViewPillsHistory
        adapter = pillsHistoryAdapter
        addLinearSpaceItemDecoration(R.dimen.padding_8)
    }

    private fun setProfileSnapshotTask(task: Task<DocumentSnapshot>) {
        task.addOnSuccessListener { viewModel.getProfile(it) }
        task.addOnFailureListener { Timber.d(it.message) }
    }

    private fun setPillsSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getPillsHistory(it) }
        addOnFailureListener { Timber.d(it.message) }
    }
}