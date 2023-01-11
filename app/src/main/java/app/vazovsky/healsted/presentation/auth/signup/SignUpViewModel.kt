package app.vazovsky.healsted.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.SaveAccountUseCase
import app.vazovsky.healsted.domain.auth.SaveUserUseCase
import app.vazovsky.healsted.domain.auth.SignUpUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.health.SaveMonitoringAttributeUseCase
import app.vazovsky.healsted.domain.profile.SaveLoyaltyUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val destinations: SignUpDestinations,
    private val signUpUseCase: SignUpUseCase,
    private val saveAccountUseCase: SaveAccountUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveLoyaltyUseCase: SaveLoyaltyUseCase,
    private val saveMonitoringAttributeUseCase: SaveMonitoringAttributeUseCase,
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

    /** Добавление первого значения Веса в аккаунт */
    private val _saveWeightLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveWeightLiveData: LiveData<LoadableResult<Task<Void>>> = _saveWeightLiveData

    /** Добавление первого значения Роста в аккаунт */
    private val _saveHeightLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveHeightLiveData: LiveData<LoadableResult<Task<Void>>> = _saveHeightLiveData

    /** Добавление первого значения Температуры в аккаунт */
    private val _saveTemperatureLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveTemperatureLiveData: LiveData<LoadableResult<Task<Void>>> = _saveTemperatureLiveData

    /** Добавление первого значения Температуры в аккаунт */
    private val _saveBloodPressureLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val saveBloodPressureLiveData: LiveData<LoadableResult<Task<Void>>> = _saveBloodPressureLiveData

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
        birthday: Timestamp? = null,
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

    /** Открыть дашборд */
    fun openDashboard() {
        navigate(destinations.dashboard())
    }

    /** Сохранение веса */
    fun saveWeight() {
        _saveWeightLiveData.launchLoadData(
            saveMonitoringAttributeUseCase.executeFlow(
                SaveMonitoringAttributeUseCase.Params(
                    MonitoringAttribute(
                        value = "50",
                        type = MonitoringType.WEIGHT,
                    )
                )
            )
        )
    }

    /** Сохранение роста */
    fun saveHeight() {
        _saveHeightLiveData.launchLoadData(
            saveMonitoringAttributeUseCase.executeFlow(
                SaveMonitoringAttributeUseCase.Params(
                    MonitoringAttribute(
                        value = "150",
                        type = MonitoringType.HEIGHT,
                    )
                )
            )
        )
    }

    /** Сохранение температуры */
    fun saveTemperature() {
        _saveTemperatureLiveData.launchLoadData(
            saveMonitoringAttributeUseCase.executeFlow(
                SaveMonitoringAttributeUseCase.Params(
                    MonitoringAttribute(
                        value = "36.6",
                        type = MonitoringType.TEMPERATURE,
                    )
                )
            )
        )
    }

    /** Сохранение давления */
    fun saveBloodPressure() {
        _saveBloodPressureLiveData.launchLoadData(
            saveMonitoringAttributeUseCase.executeFlow(
                SaveMonitoringAttributeUseCase.Params(
                    MonitoringAttribute(
                        value = "120/80",
                        type = MonitoringType.BLOOD_PRESSURE,
                    )
                )
            )
        )
    }
}