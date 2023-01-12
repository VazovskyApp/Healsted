package app.vazovsky.healsted.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.FragmentSignUpBinding
import app.vazovsky.healsted.domain.auth.SaveFireStoreUserUseCase
import app.vazovsky.healsted.extensions.checkInputs
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.managers.FirebaseAuthExceptionManager
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.QuerySnapshot
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
        signUpResultLiveEvent.observe { result ->
            result.doOnSuccess { setSignUpTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveUserLiveEvent.observe { result ->
            result.doOnSuccess { setSaveUserTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveAccountLiveEvent.observe { result ->
            result.doOnSuccess { setSaveAccountTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveLoyaltyLiveEvent.observe { result ->
            result.doOnSuccess { setSaveLoyaltyTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveWeightLiveEvent.observe { result ->
            result.doOnSuccess { setSaveWeightTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveHeightLiveEvent.observe { result ->
            result.doOnSuccess { setSaveHeightTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveTemperatureLiveEvent.observe { result ->
            result.doOnSuccess { setSaveTemperatureTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveBloodPressureLiveEvent.observe { result ->
            result.doOnSuccess { setSaveBloodPressureTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        listPillsSnapshotLiveEvent.observe { result ->
            result.doOnSuccess { setListPillsSnapshotTask(it) }
            result.doOnFailure { Timber.d(it.message) }
        }
        listPillsLiveEvent.observe { result ->
            result.doOnSuccess { pills -> viewModel.saveLocalPills(pills) }
            result.doOnFailure { Timber.d(it.message) }
        }
        saveLocalPillsLiveEvent.observe { result ->
            result.doOnSuccess { viewModel.openDashboard() }
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

    private fun setSaveUserTask(saveUserResult: SaveFireStoreUserUseCase.Result) = with(saveUserResult.task) {
        addOnSuccessListener {
            viewModel.saveAccount(
                uid = saveUserResult.uid,
                accountHolder = saveUserResult.user,
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
        addOnSuccessListener { viewModel.getPillsSnapshot() }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setListPillsSnapshotTask(task: Task<QuerySnapshot>) = with(task) {
        addOnSuccessListener { viewModel.getPills(it) }
        addOnFailureListener { Timber.d(it.message) }
    }
}