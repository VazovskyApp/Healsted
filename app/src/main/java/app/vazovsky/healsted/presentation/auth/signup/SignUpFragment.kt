package app.vazovsky.healsted.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.databinding.FragmentSignUpBinding
import app.vazovsky.healsted.domain.auth.SaveUserUseCase
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.managers.FirebaseAuthExceptionManager
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    @Inject lateinit var firebaseAuthExceptionManager: FirebaseAuthExceptionManager
    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        signUpResultLiveData.observe { result ->
            result.doOnSuccess { task ->
                setSignUpTask(task)
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
        saveUserLiveData.observe { result ->
            result.doOnSuccess { saveUserResult ->
                setSaveUserTask(saveUserResult)
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
        saveAccountLiveData.observe { result ->
            result.doOnSuccess { task ->
                setSaveAccountTask(task)
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()

        setupToolbar()
        setupSignUp()
    }

    private fun setSignUpTask(task: Task<AuthResult>) {
        task.addOnSuccessListener { authResult ->
            Timber.d(authResult.user?.email)
            authResult.user?.let { user ->
                viewModel.saveUser(email = user.email.toString())
            }
        }
        task.addOnFailureListener { exception ->
            showErrorSnackbar(
                message = if (exception is FirebaseAuthException) {
                    firebaseAuthExceptionManager.getErrorMessage(exception)
                } else {
                    exception.localizedMessage
                }
            )
            Timber.d(exception.localizedMessage)
        }
    }

    private fun setSaveUserTask(saveUserResult: SaveUserUseCase.Result) {
        saveUserResult.task.apply {
            addOnSuccessListener {
                viewModel.saveAccount(
                    accountHolder = User(email = saveUserResult.email, ""),
                    nickname = binding.editTextNickname.text.toString(),
                )
            }
            addOnFailureListener { exception ->
                Timber.d(exception.localizedMessage)
            }
        }
    }

    private fun setSaveAccountTask(task: Task<Void>) {
        task.apply {
            addOnSuccessListener {
                viewModel.openDashboard()
            }
            addOnFailureListener { exception ->
                Timber.d(exception.localizedMessage)
            }
        }
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
    }

    private fun setupSignUp() = with(binding) {
        buttonConfirm.setOnClickListener {
            val nickname = editTextNickname.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (nickname.isBlank() || email.isBlank() || password.isBlank()) {
                showErrorSnackbar(message = requireContext().getString(R.string.auth_empty_data))
            } else {
                viewModel.signUp(nickname, email, password)
            }
        }
    }
}