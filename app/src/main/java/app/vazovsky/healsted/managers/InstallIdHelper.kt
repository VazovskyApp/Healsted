package app.vazovsky.healsted.managers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

private const val PREFS_INSTALL_FILE_NAME = "install_info"
private const val PREFS_INSTALL_ID = "install_id"

/** InstallId приложения (меняется только при переустановке приложения) */
class InstallIdHelper @Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val prefs by lazy { context.getSharedPreferences(PREFS_INSTALL_FILE_NAME, Context.MODE_PRIVATE) }

    private val deviceInstallUuid: UUID

    val healstedInstallId: String
        get() = deviceInstallUuid.toString().uppercase(Locale.getDefault())

    init {
        val installId = prefs.getString(PREFS_INSTALL_ID, null)

        deviceInstallUuid = if (installId != null) {
            UUID.fromString(installId)
        } else {
            val newUuid = UUID.randomUUID()

            prefs.edit().putString(PREFS_INSTALL_ID, newUuid.toString()).apply()
            newUuid
        }
    }
}
