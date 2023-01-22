package app.vazovsky.healsted.data.appinfo.repository

import app.vazovsky.healsted.BuildConfig
import javax.inject.Inject

class BuildConfigProviderImpl @Inject constructor() : BuildConfigProvider {
    override val buildType: String get() = BuildConfig.BUILD_TYPE
    override val versionCode: Int get() = BuildConfig.VERSION_CODE
    override val versionName: String get() = BuildConfig.VERSION_NAME
}