package app.vazovsky.healsted.data.appinfo.repository

import app.vazovsky.healsted.data.appinfo.EndpointEnvironment

private const val ENVIRONMENT_PREF = "pref_environment"
private const val KEY_ENVIRONMENT = "key_current_environment"

/** Информация о приложении */
interface AppInfoRepository {
    val isRelease: Boolean
    val isDebug: Boolean
    val isBeta: Boolean

    val installId: String

    val versionName: String
    val versionNameWithCode: String
    val versionCode: String

    /** Уникальный ID девайса, который генерируется локально */
    val deviceUuid: String

    val phoneModelWithVendor: String

    companion object {
        // Build types
        const val RELEASE = "release"
        const val BETA = "beta"
        const val DEBUG = "debug"

        // Flavors
        const val PROD_FLAVOR = "prod"
        const val DEV_FLAVOR = "dev"

        const val DEFAULT_COUNTRY_CODE = "RU"
    }
}