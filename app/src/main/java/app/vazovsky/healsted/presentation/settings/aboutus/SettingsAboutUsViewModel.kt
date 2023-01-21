package app.vazovsky.healsted.presentation.settings.aboutus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.settings.GetVersionNameUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsAboutUsViewModel @Inject constructor(
    private val destinations: SettingsAboutUsDestinations,
    private val getVersionNameUseCase: GetVersionNameUseCase,
) : BaseViewModel() {

    /** Версия приложения */
    private val _appVersionLiveData = MutableLiveData<LoadableResult<String>>()
    val appVersionLiveData: LiveData<LoadableResult<String>> = _appVersionLiveData

    /** Поделиться приложением */
    fun shareUrl() {
        navigate(destinations.shareUrl())
    }

    /** Открыть телеграм канал */
    fun openTelegramChannel() {
        navigate(destinations.telegramChannel())
    }

    /** Открыть чат с разработчиком */
    fun openTelegramVazovsky() {
        navigate(destinations.telegramVazovsky())
    }

    /** Получение версии приложения */
    fun getVersionName() {
        _appVersionLiveData.launchLoadData(
            getVersionNameUseCase.executeFlow(UseCase.None)
        )
    }
}