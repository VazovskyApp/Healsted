package app.vazovsky.mypills.domain.settings

import app.vazovsky.mypills.data.model.SettingsItem
import app.vazovsky.mypills.data.repository.SettingsRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение настроек */
class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : UseCaseUnary<UseCase.None, List<SettingsItem>>() {
    override suspend fun execute(params: UseCase.None): List<SettingsItem> {
        return settingsRepository.getSettings()
    }
}