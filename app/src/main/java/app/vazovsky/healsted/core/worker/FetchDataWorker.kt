package app.vazovsky.healsted.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.core.model.DataState
import app.vazovsky.healsted.core.repository.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import timber.log.Timber

private const val RESPONSE_ID = "id"
private const val RESPONSE_TITLE = "title"
private const val RESPONSE_CONTENT = "content"
private const val RESPONSE_CLICK_REFERRER = "click_referrer"
private const val RESPONSE_DATA = "data"

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepository,
    private val notificationCore: NotificationCore
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val endPoint = inputData.getString(NotificationCore.ENDPOINT_REQUEST)
        val token = inputData.getString(NotificationCore.TOKEN)
        val deviceId = inputData.getString(NotificationCore.DEVICE_ID)
        val packageName = inputData.getString(NotificationCore.PACKAGE_NAME)
        val className = inputData.getString(NotificationCore.CLASS_NAME)
        val notificationImage = inputData.getInt(
            NotificationCore.NOTIFICATION_IMAGE,
            androidx.appcompat.R.drawable.btn_radio_off_mtrl
        )

        if (endPoint != null && token != null && deviceId != null) {
            getData(endPoint, token, deviceId, notificationImage, packageName, className)
        }

        val outputData = Data.Builder()
            .putString(NotificationCore.NOTIFICATION_DATA, "Notification data")
            .build()

        return Result.success(outputData)
    }

    /** Получение данных из запроса */
    private suspend fun getData(
        endPoint: String,
        token: String,
        deviceId: String,
        notificationImage: Int,
        packageName: String?,
        className: String?
    ) {
        notificationRepository.getNotification(endPoint, token, deviceId)
            .catch {
                Timber.d("OH DAMN IT, WE GOT ERROR (in catch): ${it.message}")
            }
            .collect { notificationRes ->
                when (notificationRes) {
                    is DataState.Success -> {
                        try {
                            val response = notificationRes.data?.get(0)?.asJsonObject

                            val id: String =
                                if (response?.has(RESPONSE_ID) == true)
                                    response.get(RESPONSE_ID).toString().replace("\"", "")
                                else
                                    "1"

                            val title: String =
                                if (response?.has(RESPONSE_TITLE) == true)
                                    response.get(RESPONSE_TITLE).toString().replace("\"", "")
                                else
                                    "title"

                            val content: String =
                                if (response?.has(RESPONSE_CONTENT) == true)
                                    response.get(RESPONSE_CONTENT).toString().replace("\"", "")
                                else
                                    "content"

                            val clickReferrerEndPoint: String =
                                if (response?.has(RESPONSE_CLICK_REFERRER) == true) {
                                    response.get(RESPONSE_CLICK_REFERRER).toString().replace("\"", "")
                                } else {
                                    // TODO изменить
                                    "https://automation.basalam.com/api/api_v1.0/notifications/push/read/{notification_id}"
                                }
                            val data = response?.getAsJsonObject(RESPONSE_DATA)

                            notificationCore.sendOnDefaultChannel(
                                applicationContext,
                                id,
                                notificationImage,
                                data,
                                title,
                                content,
                                packageName!!,
                                className!!,
                                clickReferrerEndPoint
                            )
                        } catch (e: Exception) {
                            Timber.d("LOL OH DAMN IT, WE GOT ERROR (in collect catch): ${e.message}")
                        }
                    }

                    else -> Timber.d("LOL OH DAMN IT, WE GOT SERVER ERROR")
                }
            }
    }
}