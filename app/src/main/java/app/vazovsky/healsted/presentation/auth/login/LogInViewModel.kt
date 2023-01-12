package app.vazovsky.healsted.presentation.auth.login

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.LogInUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.GetAllPillsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToAllPillsUseCase
import app.vazovsky.healsted.domain.pills.SaveLocalPillsUseCase
import app.vazovsky.healsted.domain.profile.GetProfileUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val destinations: LogInDestinations,
    private val logInUseCase: LogInUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getAllPillsUseCase: GetAllPillsUseCase,
    private val parseSnapshotToAllPillsUseCase: ParseSnapshotToAllPillsUseCase,
    private val saveLocalPillsUseCase: SaveLocalPillsUseCase,
) : BaseViewModel() {

    /** Получение результата авторизации */
    private val _logInResultLiveEvent = SingleLiveEvent<LoadableResult<Task<AuthResult>>>()
    val logInResultLiveEvent: LiveData<LoadableResult<Task<AuthResult>>> = _logInResultLiveEvent

    /** Получение аккаунта из FireStore */
    private val _accountLiveEvent = SingleLiveEvent<LoadableResult<Task<DocumentSnapshot>>>()
    val accountLiveEvent: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _accountLiveEvent

    /** Таблетки из FireStore в виде QuerySnapshot */
    private val _listPillsSnapshotLiveEvent = SingleLiveEvent<LoadableResult<Task<QuerySnapshot>>>()
    val listPillsSnapshotLiveEvent: LiveData<LoadableResult<Task<QuerySnapshot>>> = _listPillsSnapshotLiveEvent

    /** Таблетки из FireStore из QuerySnapshot */
    private val _listPillsLiveEvent = SingleLiveEvent<LoadableResult<List<Pill>>>()
    val listPillsLiveEvent: LiveData<LoadableResult<List<Pill>>> = _listPillsLiveEvent

    /** Сохранение таблеток в локальную бд */
    private val _saveLocalPillsLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val saveLocalPillsLiveEvent: LiveData<LoadableResult<Boolean>> = _saveLocalPillsLiveEvent

    /** Авторизоваться */
    fun logIn(email: String, password: String) {
        _logInResultLiveEvent.launchLoadData(
            logInUseCase.executeFlow(LogInUseCase.Params(email, password))
        )
    }

    /** Получение аккаунта */
    fun getAccount() {
        _accountLiveEvent.launchLoadData(
            getProfileUseCase.executeFlow(UseCase.None)
        )
    }

    /** Сохранение лекарств локально */
    fun saveLocalPills(pills: List<Pill>) {
        _saveLocalPillsLiveEvent.launchLoadData(
            saveLocalPillsUseCase.executeFlow(SaveLocalPillsUseCase.Params(pills))
        )
    }

    /** Открытие дашборда */
    fun openDashboard() {
        navigate(destinations.dashboard())
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