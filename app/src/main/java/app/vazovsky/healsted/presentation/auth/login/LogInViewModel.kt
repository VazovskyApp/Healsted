package app.vazovsky.healsted.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.LogInUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val destinations: LogInDestinations,
    private val logInUseCase: LogInUseCase,
) : BaseViewModel() {

    /** Получение результата авторизации */
    private val _logInResultLiveData = MutableLiveData<LoadableResult<Task<AuthResult>>>()
    val logInResultLiveData: LiveData<LoadableResult<Task<AuthResult>>> = _logInResultLiveData

    /** Авторизоваться */
    fun logIn(email: String, password: String) {
        _logInResultLiveData.launchLoadData(
            logInUseCase.executeFlow(LogInUseCase.Params(email, password))
        )
    }

    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}