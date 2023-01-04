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
    private val destinations: SettingsDestinations,
    private val getSettingsUseCase: GetSettingsUseCase,
) : BaseViewModel() {

    /** Настройки */
    private val _settingsLiveData = MutableLiveData<LoadableResult<List<SettingsItem>>>()
    val settingsLiveData: LiveData<LoadableResult<List<SettingsItem>>> = _settingsLiveData

    /** Получение настроек */
    fun getSettings() {
        _settingsLiveData.launchLoadData(
            getSettingsUseCase.executeFlow(UseCase.None)
        )
    }

    /** Открыть авторизацию */
    fun openAuth() {
        navigate(destinations.auth())
    }

    /** Открыть настройки аккаунта */
    fun openAccount() {
        navigate(destinations.account())
    }

    /** Открыть информацию о приложении */
    fun openAboutUs() {
        navigate(destinations.aboutUs())
    }

    /** Открыть настройки уведомлений */
    fun openNotifications() {
        navigate(destinations.notification())
    }

    /** Открыть отправку данных о баге */
    fun openReportBug() {
        navigate(destinations.reportBug())
    }

    /** Открыть отправку фидбека */
    fun openSendFeedback() {
        navigate(destinations.sendFeedback())
    }
}