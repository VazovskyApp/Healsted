package app.vazovsky.healsted.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.account.GetAccountUseCase
import app.vazovsky.healsted.domain.account.ParseSnapshotToAccountUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.GetAllPillsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToPillsHistoryUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val parseSnapshotToAccountUseCase: ParseSnapshotToAccountUseCase,
    private val getAllPillsUseCase: GetAllPillsUseCase,
    private val parseSnapshotToPillsHistoryUseCase: ParseSnapshotToPillsHistoryUseCase,
) : BaseViewModel() {
    /** Данные об аккаунте */
    private val _profileSnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val profileSnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _profileSnapshotLiveData

    /** Данные об аккаунте */
    private val _profileLiveData = MutableLiveData<LoadableResult<Account>>()
    val profileLiveData: LiveData<LoadableResult<Account>> = _profileLiveData

    /** Таблетки из FireStore в виде QuerySnapshot */
    private val _listPillsSnapshotLiveData = MutableLiveData<LoadableResult<Task<QuerySnapshot>>>()
    val listPillsSnapshotLiveData: LiveData<LoadableResult<Task<QuerySnapshot>>> = _listPillsSnapshotLiveData

    /** Таблетки из FireStore из QuerySnapshot */
    private val _listPillsHistoryLiveData = MutableLiveData<LoadableResult<List<TodayPillItem>>>()
    val listPillsHistoryLiveData: LiveData<LoadableResult<List<TodayPillItem>>> = _listPillsHistoryLiveData

    /** Получение данных об аккаунте */
    fun getProfileSnapshot() {
        _profileSnapshotLiveData.launchLoadData(
            getAccountUseCase.executeFlow(UseCase.None)
        )
    }

    fun getProfile(snapshot: DocumentSnapshot) {
        _profileLiveData.launchLoadData(
            parseSnapshotToAccountUseCase.executeFlow(ParseSnapshotToAccountUseCase.Params(snapshot))
        )
    }

    fun getPillsHistorySnapshot() {
        _listPillsSnapshotLiveData.launchLoadData(
            getAllPillsUseCase.executeFlow(UseCase.None)
        )
    }

    fun getPillsHistory(snapshot: QuerySnapshot) {
        _listPillsHistoryLiveData.launchLoadData(
            parseSnapshotToPillsHistoryUseCase.executeFlow(ParseSnapshotToPillsHistoryUseCase.Params(snapshot))
        )
    }
}