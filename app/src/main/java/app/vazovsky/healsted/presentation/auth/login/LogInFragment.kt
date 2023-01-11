package app.vazovsky.healsted.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
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
            result.doOnSuccess { setLogInTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        accountLiveData.observe { result ->
            result.doOnSuccess { setAccountTask(it) }
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
        addOnSuccessListener { viewModel.openDashboard() }
        addOnFailureListener { Timber.d(it.message) }
    }
}