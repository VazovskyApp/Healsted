package app.vazovsky.healsted.domain.settings

import app.vazovsky.healsted.data.model.SettingsItem
import app.vazovsky.healsted.data.repository.SettingsRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение настроек */
class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : UseCaseUnary<UseCase.None, List<SettingsItem>>() {
    override suspend fun execute(params: UseCase.None): List<SettingsItem> {
        return settingsRepository.getSettings()
    }
}