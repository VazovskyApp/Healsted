package app.vazovsky.healsted.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.databinding.FragmentLogInBinding
import app.vazovsky.healsted.extensions.checkInputs
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.managers.FirebaseAuthExceptionManager
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран авторизации */
@AndroidEntryPoint
class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    private val binding by viewBinding(FragmentLogInBinding::bind)
    private val viewModel: LogInViewModel by viewModels()

    @Inject lateinit var firebaseAuthExceptionManager: FirebaseAuthExceptionManager
    @Inject lateinit var notificationCore: NotificationCore

    override fun callOperations() = Unit
    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        logInResultLiveEvent.observe { result ->
            result.doOnSuccess { setLogInTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        accountLiveEvent.observe { result ->
            result.doOnSuccess { setAccountTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        listPillsSnapshotLiveEvent.observe { result ->
            result.doOnSuccess { setListPillsSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        listPillsLiveEvent.observe { result ->
            result.doOnSuccess { parseResult ->
                viewModel.saveRoomPills(parseResult.pills)
                parseResult.pills.forEach { pill ->
                    try {
                        notificationCore.cancelWorker(requireActivity().application, pill.id)
                        notificationCore.createWorker(
                            application = requireActivity().application,
                            notificationImage = R.drawable.ic_logo_red,
                            uid = parseResult.uid,
                            pill = pill,
                        )
                    } catch (e: Exception) {
                        Timber.d(e.message)
                    }
                }
            }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveRoomPillsLiveEvent.observe { result ->
            result.doOnSuccess { viewModel.openDashboard() }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }

        setupLogIn()
    }

    private fun setupLogIn() = with(binding) {
        buttonConfirm.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            val validated = listOf(textInputEmail, textInputPassword).checkInputs()
            if (validated) viewModel.logIn(email, password)
        }
    }

    private fun setLogInTask(task: Task<AuthResult>) = with(task) {
        addOnSuccessListener { viewModel.getAccount() }
        addOnFailureListener { exception ->
            showErrorSnackbar(
                message = if (exception is FirebaseAuthException) firebaseAuthExceptionManager.getErrorMessage(exception)
                else exception.localizedMessage
            )
        }
    }

    private fun setAccountTask(task: Task<DocumentSnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getPillsSnapshot() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setListPillsSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getPills(it) }
        addOnFailureListener { Timber.d(it.message) }
    }
}