package app.vazovsky.healsted.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.SettingsItem
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.settings.GetSettingsUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
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