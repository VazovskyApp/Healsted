package app.vazovsky.healsted.data.appinfo.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import app.vazovsky.healsted.data.appinfo.DeviceUuidFactory
import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepository.Companion.BETA
import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepository.Companion.DEBUG
import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepository.Companion.RELEASE
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.managers.InstallIdHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import timber.log.Timber

class AppInfoRepositoryImpl @Inject constructor(
    buildConfigProvider: BuildConfigProvider,
    installIdHelper: InstallIdHelper,
    private val deviceUuidFactory: DeviceUuidFactory,
    @ApplicationContext val context: Context,
) : AppInfoRepository {

    override val isRelease: Boolean = buildConfigProvider.buildType == RELEASE
    override val isDebug: Boolean = buildConfigProvider.buildType == DEBUG
    override val isBeta: Boolean = buildConfigProvider.buildType == BETA

    override val installId: String = installIdHelper.healstedInstallId

    override val versionName: String
        get() {
            var version = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                version = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e(e)
            }

            return version
        }

    override val versionNameWithCode: String
        get() {
            var version = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                version = pInfo.versionName
                val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    pInfo.longVersionCode
                } else {
                    @Suppress("DEPRECATION")
                    pInfo.versionCode.toLong()
                }
                version += " ($versionCode)"
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e(e)
            }

            return version
        }

    override val versionCode: String = buildConfigProvider.versionCode.toString()

    override val deviceUuid: String
        get() = deviceUuidFactory.deviceUuid.toString()

    override val phoneModelWithVendor: String = "${Build.MANUFACTURER.capitalizeFirstChar(Locale.getDefault())} ${Build.MODEL}"
}