package app.vazovsky.healsted.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.databinding.FragmentLogInBinding
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.managers.FirebaseAuthExceptionManager
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран авторизации */
@AndroidEntryPoint
class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    private val binding by viewBinding(FragmentLogInBinding::bind)
    private val viewModel: LogInViewModel by viewModels()

    @Inject lateinit var firebaseAuthExceptionManager: FirebaseAuthExceptionManager

    override fun callOperations() = Unit
    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        logInResultLiveData.observe { result ->
            result.doOnSuccess { task ->
                setLogInTask(task)
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
        accountLiveData.observe { result ->
            result.doOnSuccess { task ->
                setAccountTask(task)
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
    }

    private fun setLogInTask(task: Task<AuthResult>) {
        task.addOnSuccessListener { authResult ->
            authResult.user?.uid?.let { viewModel.getAccount(it) }
        }
        task.addOnFailureListener { exception ->
            showErrorSnackbar(
                message = if (exception is FirebaseAuthException) {
                    firebaseAuthExceptionManager.getErrorMessage(exception)
                } else {
                    exception.localizedMessage
                }
            )
        }
    }

    private fun setAccountTask(task: Task<DocumentSnapshot>) {
        task.apply {
            addOnSuccessListener {
                viewModel.openDashboard()
            }
            addOnFailureListener { exception ->
                Timber.d(exception.localizedMessage)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()

        setupToolbar()
        setupLogIn()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
    }

    private fun setupLogIn() = with(binding) {
        buttonConfirm.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                showErrorSnackbar(message = requireContext().getString(R.string.auth_empty_data))
            } else {
                viewModel.logIn(email, password)
            }
        }
    }
}