package app.vazovsky.healsted.presentation.settings.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.auth.DeleteFirestoreAccountUseCase
import app.vazovsky.healsted.domain.auth.DeleteAccountUseCase
import app.vazovsky.healsted.domain.auth.UpdateFireStoreAccountUseCase
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.account.GetAccountUseCase
import app.vazovsky.healsted.domain.account.ParseSnapshotToAccountUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SettingsAccountViewModel @Inject constructor(
    private val outDestinations: SettingsAccountOutDestinations,
    private val getAccountUseCase: GetAccountUseCase,
    private val parseSnapshotToAccountUseCase: ParseSnapshotToAccountUseCase,
    private val updateFireStoreAccountUseCase: UpdateFireStoreAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteFirestoreAccountUseCase: DeleteFirestoreAccountUseCase,
) : BaseViewModel() {

    var birthday: LocalDate? = null

    /** Профиль в виде DocumentSnapshot */
    private val _profileSnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val profileSnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _profileSnapshotLiveData

    /** Профиль */
    private val _profileLiveData = MutableLiveData<LoadableResult<Account>>()
    val profileLiveData: LiveData<LoadableResult<Account>> = _profileLiveData

    /** Обновление аккаунта */
    private val _editAccountLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val editAccountLiveEvent: LiveData<LoadableResult<Task<Void>>> = _editAccountLiveEvent

    /** Удаление аккаунта из FireStore */
    private val _deleteAccountFireStoreLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val deleteAccountFireStoreLiveData: LiveData<LoadableResult<Task<Void>>> = _deleteAccountFireStoreLiveData

    /** Удаление аккаунта из Firebase */
    private val _deleteAccountFirebaseLiveData = MutableLiveData<LoadableResult<DeleteAccountUseCase.Result>>()
    val deleteAccountFirebaseLiveData: LiveData<LoadableResult<DeleteAccountUseCase.Result>> = _deleteAccountFirebaseLiveData


    /** Получить профиль в виде DocumentSnapshot */
    fun getProfileSnapshot() {
        _profileSnapshotLiveData.launchLoadData(
            getAccountUseCase.executeFlow(UseCase.None)
        )
    }

    /** Получение профиля */
    fun getProfile(snapshot: DocumentSnapshot) {
        _profileLiveData.launchLoadData(
            parseSnapshotToAccountUseCase.executeFlow(ParseSnapshotToAccountUseCase.Params(snapshot))
        )
    }

    /** Обновление аккаунта */
    fun updateAccount(account: Account) {
        _editAccountLiveEvent.launchLoadData(
            updateFireStoreAccountUseCase.executeFlow(UpdateFireStoreAccountUseCase.Params(account))
        )
    }

    /** Удаление аккаунта из FireStore */
    fun deleteAccountFireStore() {
        _deleteAccountFireStoreLiveData.launchLoadData(
            deleteFirestoreAccountUseCase.executeFlow(UseCase.None)
        )
    }

    /** Удаление аккаунта из Firebase */
    fun deleteAccountFirebase() {
        _deleteAccountFirebaseLiveData.launchLoadData(
            deleteAccountUseCase.executeFlow(UseCase.None)
        )
    }

    fun openAuth() {
        navigate(outDestinations.auth())
    }
}