package app.vazovsky.mypills.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.mypills.data.model.SettingsItem
import app.vazovsky.mypills.data.model.base.LoadableResult
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.settings.GetSettingsUseCase
import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDestinations: SettingsDestinations,
    private val getSettingsUseCase: GetSettingsUseCase,
) : BaseViewModel() {

    /** Настройки */
    private val _settingsLiveData = MutableLiveData<LoadableResult<List<SettingsItem>>>()
    val settingsLiveData: LiveData<LoadableResult<List<SettingsItem>>> = _settingsLiveData

    /** Получение настроек */
    fun getSettings() {
        _settingsLiveData.launchLoadData(getSettingsUseCase.executeFlow(UseCase.None))
    }

    fun openAuth() {
        navigate(settingsDestinations.auth())
    }

    fun openAccount() {
        navigate(settingsDestinations.account())
    }

    fun openAboutUs() {
        navigate(settingsDestinations.aboutUs())
    }

    fun openNotifications() {
        navigate(settingsDestinations.notification())
    }

    fun openReportBug() {
        navigate(settingsDestinations.reportBug())
    }

    fun openSendFeedback() {
        navigate(settingsDestinations.sendFeedback())
    }
}