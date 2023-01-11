package app.vazovsky.healsted.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.databinding.FragmentSignUpBinding
import app.vazovsky.healsted.domain.auth.SaveUserUseCase
import app.vazovsky.healsted.extensions.checkInputs
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

/** Экран регистрации */
@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    @Inject lateinit var firebaseAuthExceptionManager: FirebaseAuthExceptionManager
    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        signUpResultLiveData.observe { result ->
            result.doOnSuccess { setSignUpTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveUserLiveData.observe { result ->
            result.doOnSuccess { setSaveUserTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveAccountLiveData.observe { result ->
            result.doOnSuccess { setSaveAccountTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveLoyaltyLiveData.observe { result ->
            result.doOnSuccess { setSaveLoyaltyTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveWeightLiveData.observe { result ->
            result.doOnSuccess { setSaveWeightTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveHeightLiveData.observe { result ->
            result.doOnSuccess { setSaveHeightTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveTemperatureLiveData.observe { result ->
            result.doOnSuccess { setSaveTemperatureTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveBloodPressureLiveData.observe { result ->
            result.doOnSuccess { setSaveBloodPressureTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()
        toolbar.setNavigationOnClickListener { viewModel.navigateBack() }

        setupSignUp()
    }

    private fun setupSignUp() = with(binding) {
        buttonConfirm.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            val validated = listOf(textInputEmail, textInputNickname, textInputPassword).checkInputs()
            if (validated) viewModel.signUp(email, password)
        }
    }

    private fun setSignUpTask(task: Task<AuthResult>) = with(task) {
        addOnSuccessListener { it.user?.let { user -> viewModel.saveUser(user.uid, user.email.toString()) } }
        addOnFailureListener { exception ->
            showErrorSnackbar(
                message = if (exception is FirebaseAuthException) {
                    firebaseAuthExceptionManager.getErrorMessage(exception)
                } else {
                    exception.localizedMessage
                }
            )
        }
    }

    private fun setSaveUserTask(saveUserResult: SaveUserUseCase.Result) = with(saveUserResult.task) {
        addOnSuccessListener {
            viewModel.saveAccount(
                uid = saveUserResult.uid,
                accountHolder = User(email = saveUserResult.email, ""),
                nickname = binding.editTextNickname.text.toString(),
            )
        }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveAccountTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.saveLoyalty() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveLoyaltyTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.saveWeight() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveWeightTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.saveHeight() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveHeightTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.saveTemperature() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveTemperatureTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.saveBloodPressure() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setSaveBloodPressureTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.openDashboard() }
        addOnFailureListener { Timber.d(it.message) }
    }
}