package app.vazovsky.healsted.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.SaveAccountUseCase
import app.vazovsky.healsted.domain.auth.SaveUserUseCase
import app.vazovsky.healsted.domain.auth.SignUpUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val destinations: SignUpDestinations,
    private val signUpUseCase: SignUpUseCase,
    private val saveAccountUseCase: SaveAccountUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : BaseViewModel() {

    /** Получение результата регистрации */
    private val _signUpResultLiveData = MutableLiveData<LoadableResult<Task<AuthResult>>>()
    val signUpResultLiveData: LiveData<LoadableResult<Task<AuthResult>>> = _signUpResultLiveData

    /** Сохранение пользователя в FireStore */
    private val _saveUserLiveData = MutableLiveData<LoadableResult<SaveUserUseCase.Result>>()
    val saveUserLiveData: LiveData<LoadableResult<SaveUserUseCase.Result>> = _saveUserLiveData

    /** Сохранение аккаунта в FireStore */
    private val _saveAccountLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveAccountLiveData: LiveData<LoadableResult<Task<Void>>> = _saveAccountLiveData

    /** Зарегистрироваться */
    fun signUp(nickname: String, email: String, password: String) {
        _signUpResultLiveData.launchLoadData(
            signUpUseCase.executeFlow(
                SignUpUseCase.Params(
                    nickname = nickname,
                    email = email,
                    password = password,
                )
            )
        )
    }

    /** Сохранить пользователя в FireStore */
    fun saveUser(email: String) {
        _saveUserLiveData.launchLoadData(
            saveUserUseCase.executeFlow(
                SaveUserUseCase.Params(
                    email = email
                )
            )
        )
    }

    /** Сохранить аккаунт в FireStore */
    fun saveAccount(
        accountHolder: User,
        nickname: String,
        name: String = "",
        surname: String = "",
        patronymic: String = "",
        birthday: LocalDate? = null,
        avatar: String? = null,
        level: AccountLevel = AccountLevel.BACTERIA,
    ) {
        _saveAccountLiveData.launchLoadData(
            saveAccountUseCase.executeFlow(
                SaveAccountUseCase.Params(
                    accountHolder = accountHolder,
                    nickname = nickname,
                    name = name,
                    surname = surname,
                    patronymic = patronymic,
                    birthday = birthday,
                    avatar = avatar,
                    level = level,
                )
            )
        )
    }

    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}