package app.vazovsky.healsted.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.SignUpUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val destinations: SignUpDestinations,
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel() {

    /** Получение результата регистрации */
    private val _signUpResultLiveData = MutableLiveData<LoadableResult<Task<AuthResult>>>()
    val signUpResultLiveData: LiveData<LoadableResult<Task<AuthResult>>> = _signUpResultLiveData

    /** Зарегистрироваться */
    fun signUp(nickname: String, email: String, password: String) {
        _signUpResultLiveData.launchLoadData(
            signUpUseCase.executeFlow(SignUpUseCase.Params(nickname, email, password))
        )
    }

    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}