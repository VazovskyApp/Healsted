package app.vazovsky.healsted.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.SaveAccountUseCase
import app.vazovsky.healsted.domain.profile.SaveLoyaltyUseCase
import app.vazovsky.healsted.domain.mood.SaveMoodUseCase
import app.vazovsky.healsted.domain.pills.SavePillUseCase
import app.vazovsky.healsted.domain.auth.SaveUserUseCase
import app.vazovsky.healsted.domain.auth.SignUpUseCase
import app.vazovsky.healsted.domain.base.UseCase
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
    private val saveLoyaltyUseCase: SaveLoyaltyUseCase,
    private val savePillUseCase: SavePillUseCase,
    private val saveMoodUseCase: SaveMoodUseCase,
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

    /** Добавление аккаунта в программу лояльности */
    private val _saveLoyaltyLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveLoyaltyLiveData: LiveData<LoadableResult<Task<Void>>> = _saveLoyaltyLiveData

    /** Добавление пустого списка таблеток для аккаунта */
    private val _savePillsLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val savePillsLiveData: LiveData<LoadableResult<Task<Void>>> = _savePillsLiveData

    /** Добавление настроений для аккаунта */
    private val _saveMoodsLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveMoodsLiveData: LiveData<LoadableResult<Task<Void>>> = _saveMoodsLiveData

    /** Зарегистрироваться */
    fun signUp(email: String, password: String) {
        _signUpResultLiveData.launchLoadData(
            signUpUseCase.executeFlow(
                SignUpUseCase.Params(
                    email = email,
                    password = password,
                )
            )
        )
    }

    /** Сохранить пользователя в FireStore */
    fun saveUser(uid: String, email: String, phoneNumber: String = "") {
        _saveUserLiveData.launchLoadData(
            saveUserUseCase.executeFlow(
                SaveUserUseCase.Params(
                    uid = uid,
                    email = email,
                    phoneNumber = phoneNumber,
                )
            )
        )
    }

    /** Сохранить аккаунт в FireStore */
    fun saveAccount(
        uid: String,
        accountHolder: User,
        nickname: String,
        name: String = "",
        surname: String = "",
        patronymic: String = "",
        birthday: LocalDate? = null,
        avatar: String? = null,
    ) {
        _saveAccountLiveData.launchLoadData(
            saveAccountUseCase.executeFlow(
                SaveAccountUseCase.Params(
                    uid = uid,
                    accountHolder = accountHolder,
                    nickname = nickname,
                    name = name,
                    surname = surname,
                    patronymic = patronymic,
                    birthday = birthday,
                    avatar = avatar,
                )
            )
        )
    }

    /** Добавить аккаунт в программу лояльности */
    fun saveLoyalty() {
        _saveLoyaltyLiveData.launchLoadData(
            saveLoyaltyUseCase.executeFlow(UseCase.None)
        )
    }

    /** Добавить пустой список таблеток */
    fun savePills() {
        _savePillsLiveData.launchLoadData(
            savePillUseCase.executeFlow(UseCase.None)
        )
    }

    /** Добавить пустой список настроений */
    fun saveMoods() {
        _saveMoodsLiveData.launchLoadData(
            saveMoodUseCase.executeFlow(UseCase.None)
        )
    }

    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}