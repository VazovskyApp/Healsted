package app.vazovsky.healsted.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.LogInUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.profile.GetProfileUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val destinations: LogInDestinations,
    private val logInUseCase: LogInUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {

    /** Получение результата авторизации */
    private val _logInResultLiveData = MutableLiveData<LoadableResult<Task<AuthResult>>>()
    val logInResultLiveData: LiveData<LoadableResult<Task<AuthResult>>> = _logInResultLiveData

    /** Получение аккаунта из FireStore */
    private val _accountLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val accountLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _accountLiveData

    /** Авторизоваться */
    fun logIn(email: String, password: String) {
        _logInResultLiveData.launchLoadData(
            logInUseCase.executeFlow(LogInUseCase.Params(email, password))
        )
    }

    /** Получение аккаунта */
    fun getAccount() {
        _accountLiveData.launchLoadData(
            getProfileUseCase.executeFlow(UseCase.None)
        )
    }

    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}