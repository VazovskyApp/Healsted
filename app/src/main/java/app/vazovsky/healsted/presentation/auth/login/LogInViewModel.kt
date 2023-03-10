package app.vazovsky.healsted.presentation.auth.login

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.account.GetAccountUseCase
import app.vazovsky.healsted.domain.auth.LogInUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.GetAllPillsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToAllPillsUseCase
import app.vazovsky.healsted.domain.roompills.SaveRoomPillsUseCase
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
    private val getAccountUseCase: GetAccountUseCase,
    private val getAllPillsUseCase: GetAllPillsUseCase,
    private val parseSnapshotToAllPillsUseCase: ParseSnapshotToAllPillsUseCase,
    private val saveRoomPillsUseCase: SaveRoomPillsUseCase,
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
    private val _listPillsLiveEvent = SingleLiveEvent<LoadableResult<ParseSnapshotToAllPillsUseCase.Result>>()
    val listPillsLiveEvent: LiveData<LoadableResult<ParseSnapshotToAllPillsUseCase.Result>> = _listPillsLiveEvent

    /** Сохранение лекарств в Room */
    private val _saveRoomPillsLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val saveRoomPillsLiveEvent: LiveData<LoadableResult<Boolean>> = _saveRoomPillsLiveEvent

    /** Авторизоваться */
    fun logIn(email: String, password: String) {
        _logInResultLiveEvent.launchLoadData(
            logInUseCase.executeFlow(LogInUseCase.Params(email, password))
        )
    }

    /** Получить аккаунт */
    fun getAccount() {
        _accountLiveEvent.launchLoadData(
            getAccountUseCase.executeFlow(UseCase.None)
        )
    }

    /** Получить все лекарства в виде QuerySnapshot */
    fun getPillsSnapshot() {
        _listPillsSnapshotLiveEvent.launchLoadData(getAllPillsUseCase.executeFlow(UseCase.None))
    }

    /** Получить все лекарства из QuerySnapshot */
    fun getPills(snapshot: QuerySnapshot) {
        _listPillsLiveEvent.launchLoadData(
            parseSnapshotToAllPillsUseCase.executeFlow(
                ParseSnapshotToAllPillsUseCase.Params(snapshot)
            )
        )
    }

    /** Сохранение лекарств в Room */
    fun saveRoomPills(pills: List<Pill>) {
        _saveRoomPillsLiveEvent.launchLoadData(
            saveRoomPillsUseCase.executeFlow(SaveRoomPillsUseCase.Params(pills))
        )
    }

    /** Открытие дашборда */
    fun openDashboard() {
        navigate(destinations.dashboard())
    }
}