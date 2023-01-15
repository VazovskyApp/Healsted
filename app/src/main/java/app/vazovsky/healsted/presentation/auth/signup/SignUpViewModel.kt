package app.vazovsky.healsted.presentation.auth.signup

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.User
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.SaveFireStoreAccountUseCase
import app.vazovsky.healsted.domain.auth.SaveFireStoreUserUseCase
import app.vazovsky.healsted.domain.auth.SignUpUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.health.AddMonitoringAttributeUseCase
import app.vazovsky.healsted.domain.loyalty.AddLoyaltyUseCase
import app.vazovsky.healsted.domain.pills.GetAllPillsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToAllPillsUseCase
import app.vazovsky.healsted.domain.roompills.SaveRoomPillsUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val destinations: SignUpDestinations,
    private val signUpUseCase: SignUpUseCase,
    private val saveFireStoreAccountUseCase: SaveFireStoreAccountUseCase,
    private val saveFireStoreUserUseCase: SaveFireStoreUserUseCase,
    private val addLoyaltyUseCase: AddLoyaltyUseCase,
    private val addMonitoringAttributeUseCase: AddMonitoringAttributeUseCase,
    private val getAllPillsUseCase: GetAllPillsUseCase,
    private val parseSnapshotToAllPillsUseCase: ParseSnapshotToAllPillsUseCase,
    private val saveRoomPillsUseCase: SaveRoomPillsUseCase,
) : BaseViewModel() {

    /** Получение результата регистрации */
    private val _signUpResultLiveEvent = SingleLiveEvent<LoadableResult<Task<AuthResult>>>()
    val signUpResultLiveEvent: LiveData<LoadableResult<Task<AuthResult>>> = _signUpResultLiveEvent

    /** Сохранение пользователя в FireStore */
    private val _saveUserLiveEvent = SingleLiveEvent<LoadableResult<SaveFireStoreUserUseCase.Result>>()
    val saveUserLiveEvent: LiveData<LoadableResult<SaveFireStoreUserUseCase.Result>> = _saveUserLiveEvent

    /** Сохранение аккаунта в FireStore */
    private val _saveAccountLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveAccountLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveAccountLiveEvent

    /** Добавление аккаунта в программу лояльности */
    private val _saveLoyaltyLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveLoyaltyLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveLoyaltyLiveEvent

    /** Добавление первого значения Веса в аккаунт */
    private val _saveWeightLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveWeightLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveWeightLiveEvent

    /** Добавление первого значения Роста в аккаунт */
    private val _saveHeightLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveHeightLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveHeightLiveEvent

    /** Добавление первого значения Температуры в аккаунт */
    private val _saveTemperatureLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveTemperatureLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveTemperatureLiveEvent

    /** Добавление первого значения Температуры в аккаунт */
    private val _saveBloodPressureLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val saveBloodPressureLiveEvent: LiveData<LoadableResult<Task<Void>>> = _saveBloodPressureLiveEvent

    /** Таблетки из FireStore в виде QuerySnapshot */
    private val _listPillsSnapshotLiveEvent = SingleLiveEvent<LoadableResult<Task<QuerySnapshot>>>()
    val listPillsSnapshotLiveEvent: LiveData<LoadableResult<Task<QuerySnapshot>>> = _listPillsSnapshotLiveEvent

    /** Таблетки из FireStore из QuerySnapshot */
    private val _listPillsLiveEvent = SingleLiveEvent<LoadableResult<ParseSnapshotToAllPillsUseCase.Result>>()
    val listPillsLiveEvent: LiveData<LoadableResult<ParseSnapshotToAllPillsUseCase.Result>> = _listPillsLiveEvent

    /** Сохранение лекарств в Room */
    private val _saveRoomPillsLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val saveRoomPillsLiveEvent: LiveData<LoadableResult<Boolean>> = _saveRoomPillsLiveEvent

    /** Зарегистрироваться */
    fun signUp(email: String, password: String) {
        _signUpResultLiveEvent.launchLoadData(
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
        _saveUserLiveEvent.launchLoadData(
            saveFireStoreUserUseCase.executeFlow(
                SaveFireStoreUserUseCase.Params(uid = uid, user = User(email, phoneNumber))
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
        _saveAccountLiveEvent.launchLoadData(
            saveFireStoreAccountUseCase.executeFlow(
                SaveFireStoreAccountUseCase.Params(
                    uid = uid, account = Account(accountHolder, nickname, name, surname, patronymic, birthday, avatar)
                )
            )
        )
    }

    /** Добавить аккаунт в программу лояльности */
    fun saveLoyalty() {
        _saveLoyaltyLiveEvent.launchLoadData(
            addLoyaltyUseCase.executeFlow(UseCase.None)
        )
    }

    /** Сохранение лекарств в Room */
    fun saveRoomPills(pills: List<Pill>) {
        _saveRoomPillsLiveEvent.launchLoadData(
            saveRoomPillsUseCase.executeFlow(SaveRoomPillsUseCase.Params(pills))
        )
    }

    /** Открыть дашборд */
    fun openDashboard() {
        navigate(destinations.dashboard())
    }

    /** Сохранение веса */
    fun saveWeight() {
        _saveWeightLiveEvent.launchLoadData(
            addMonitoringAttributeUseCase.executeFlow(
                AddMonitoringAttributeUseCase.Params(
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
        _saveHeightLiveEvent.launchLoadData(
            addMonitoringAttributeUseCase.executeFlow(
                AddMonitoringAttributeUseCase.Params(
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
        _saveTemperatureLiveEvent.launchLoadData(
            addMonitoringAttributeUseCase.executeFlow(
                AddMonitoringAttributeUseCase.Params(
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
        _saveBloodPressureLiveEvent.launchLoadData(
            addMonitoringAttributeUseCase.executeFlow(
                AddMonitoringAttributeUseCase.Params(
                    MonitoringAttribute(
                        value = "120/80",
                        type = MonitoringType.BLOOD_PRESSURE,
                    )
                )
            )
        )
    }

    /** Получить все таблетки в виде QuerySnapshot */
    fun getPillsSnapshot() {
        _listPillsSnapshotLiveEvent.launchLoadData(getAllPillsUseCase.executeFlow(UseCase.None))
    }

    /** Получить все таблетки из QuerySnapshot */
    fun getPills(snapshot: QuerySnapshot) {
        _listPillsLiveEvent.launchLoadData(
            parseSnapshotToAllPillsUseCase.executeFlow(
                ParseSnapshotToAllPillsUseCase.Params(snapshot)
            )
        )
    }
}