package app.vazovsky.healsted.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.loyalty.GetLoyaltyUseCase
import app.vazovsky.healsted.domain.account.GetAccountUseCase
import app.vazovsky.healsted.domain.loyalty.ParseSnapshotToLoyaltyUseCase
import app.vazovsky.healsted.domain.account.ParseSnapshotToAccountUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val parseSnapshotToAccountUseCase: ParseSnapshotToAccountUseCase,
    private val getLoyaltyUseCase: GetLoyaltyUseCase,
    private val parseSnapshotToLoyaltyUseCase: ParseSnapshotToLoyaltyUseCase,
) : BaseViewModel() {
    /** Данные об аккаунте */
    private val _profileSnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val profileSnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _profileSnapshotLiveData

    /** Данные об аккаунте */
    private val _profileLiveData = MutableLiveData<LoadableResult<Account>>()
    val profileLiveData: LiveData<LoadableResult<Account>> = _profileLiveData

    /** Данные об уровне в программе лояльности */
    private val _loyaltySnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val loyaltySnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _loyaltySnapshotLiveData

    /** Данные об уровне в программе лояльности */
    private val _loyaltyLiveData = MutableLiveData<LoadableResult<LoyaltyProgress>>()
    val loyaltyLiveData: LiveData<LoadableResult<LoyaltyProgress>> = _loyaltyLiveData

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

    /** Получение данных о программе лояльности */
    fun getLoyaltySnapshot() {
        _loyaltySnapshotLiveData.launchLoadData(
            getLoyaltyUseCase.executeFlow(UseCase.None)
        )
    }

    /** Получение данных о программе лояльности */
    fun getLoyalty(snapshot: DocumentSnapshot) {
        _loyaltyLiveData.launchLoadData(
            parseSnapshotToLoyaltyUseCase.executeFlow(ParseSnapshotToLoyaltyUseCase.Params(snapshot))
        )
    }
}