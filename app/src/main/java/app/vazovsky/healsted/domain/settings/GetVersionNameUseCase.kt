package app.vazovsky.healsted.domain.settings

import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import javax.inject.Inject

/** Получение названия версии приложения */
class GetVersionNameUseCase @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
) : UseCaseUnary<UseCase.None, String>() {
    override suspend fun execute(params: UseCase.None): String {
        return appInfoRepository.versionName
    }
}