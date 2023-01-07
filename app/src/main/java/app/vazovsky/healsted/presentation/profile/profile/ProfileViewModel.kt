package app.vazovsky.healsted.presentation.profile.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.profile.GetLoyaltyUseCase
import app.vazovsky.healsted.domain.profile.GetProfileUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getLoyaltyUseCase: GetLoyaltyUseCase,
) : BaseViewModel() {
    /** Данные об аккаунте */
    private val _profileLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val profileLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _profileLiveData

    /** Данные об уровне в программе лояльности */
    private val _loyaltyLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val loyaltyLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _loyaltyLiveData

    /** Получение данных об аккаунте */
    fun getProfile() {
        _profileLiveData.launchLoadData(
            getProfileUseCase.executeFlow(UseCase.None)
        )
    }

    /** Получение данных о программе лояльности */
    fun getLoyalty() {
        _loyaltyLiveData.launchLoadData(
            getLoyaltyUseCase.executeFlow(UseCase.None)
        )
    }
}