package app.vazovsky.healsted.presentation.profile.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.profile.GetProfileUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    /** Данные об аккаунте */
    private val _profileLiveData = MutableLiveData<LoadableResult<Account>>()
    val profileLiveData: LiveData<LoadableResult<Account>> = _profileLiveData

    /** Получение данных об аккаунте */
    fun getProfile() {
        _profileLiveData.launchLoadData(
            getProfileUseCase.executeFlow(UseCase.None)
        )
    }
}