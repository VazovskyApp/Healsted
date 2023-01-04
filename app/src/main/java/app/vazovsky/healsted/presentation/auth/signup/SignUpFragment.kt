package app.vazovsky.healsted.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.databinding.FragmentSignUpBinding
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        signUpResultLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener { authResult ->
                    Timber.d(authResult.user?.email)
                    authResult.user?.let { user ->
                        saveAccount(
                            accountHolder = User(email = user.email.toString(), ""),
                            nickname = binding.editTextNickname.text.toString(),
                        )
                    }
                    viewModel.openDashboard()
                }
                task.addOnFailureListener { exception ->
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> Timber.d("Нет пользователя с таким email. Зарегистрируйтесь.")
                        is FirebaseAuthEmailException -> Timber.d("Я ошибка FirebaseAuthEmailException")
                        is FirebaseAuthActionCodeException -> Timber.d("Я ошибка FirebaseAuthActionCodeException")
                        is FirebaseAuthMultiFactorException -> Timber.d("Я ошибка FirebaseAuthMultiFactorException")
                        is FirebaseAuthUserCollisionException -> Timber.d("Аккаунт с таким email уже существует")
                        is FirebaseAuthInvalidCredentialsException -> Timber.d("Неверный логин/пароль. Также может быть неверный формат адреса.")
                        is FirebaseAuthException -> Timber.d("Я ошибка FirebaseAuthException")
                    }
                    Timber.d(exception.localizedMessage)
                }
            }
            result.doOnFailure {
                Timber.d(it.message)
            }
        }
        saveAccountLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    viewModel.openDashboard()
                }
                task.addOnFailureListener { exception ->
                    Timber.d(exception.localizedMessage)
                }
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

    private fun setupToolbar() = with(binding) {
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }
    }

    private fun setupSignUp() = with(binding) {
        buttonConfirm.setOnClickListener {
            val nickname = editTextNickname.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (nickname.isBlank() || email.isBlank() || password.isBlank()) {
                showErrorSnackbar(
                    message = requireContext().getString(R.string.auth_empty_data),
                    action = {},
                )
            } else {
                viewModel.signUp(nickname, email, password)
            }
        }
    }
}