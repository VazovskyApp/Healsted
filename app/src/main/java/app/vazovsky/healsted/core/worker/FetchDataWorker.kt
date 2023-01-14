package app.vazovsky.healsted.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.core.repository.NotificationRepository
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.room.converters.DatesTakenSelectedListConverter
import app.vazovsky.healsted.data.room.converters.TimesMapConverter
import app.vazovsky.healsted.managers.DateFormatter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.util.concurrent.atomic.*


@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepository,
    private val notificationCore: NotificationCore,
    private val pillMapper: PillMapper,
    private val dateFormatter: DateFormatter,
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        var notificationId = AtomicInteger()
    }

    override suspend fun doWork(): Result {
        val endPoint = inputData.getString(NotificationCore.ENDPOINT_REQUEST)
        val token = inputData.getString(NotificationCore.TOKEN)
        val deviceId = inputData.getString(NotificationCore.DEVICE_ID)
        val packageName = inputData.getString(NotificationCore.PACKAGE_NAME)
        val className = inputData.getString(NotificationCore.CLASS_NAME)
        val notificationImage = inputData.getInt(NotificationCore.NOTIFICATION_IMAGE, R.drawable.ic_logo_red)

        val pillName = inputData.getString(NotificationCore.PILL_NAME)

        val pillTimesString = inputData.getString(NotificationCore.PILL_TIMES)
        val pillTimes = TimesMapConverter().mapStringToMap(pillTimesString)?.let { pillMapper.fromEntityToModelTime(it) }

        val pillStartDateString = inputData.getString(NotificationCore.PILL_START_DATE)
        val pillStartDate = pillStartDateString?.let { dateFormatter.parseLocalDateFromString(it) }

        val pillEndDateString = inputData.getString(NotificationCore.PILL_END_DATE)
        val pillEndDate = pillEndDateString?.let { dateFormatter.parseLocalDateFromString(it) }

        val pillDatesTakenTypeString = inputData.getString(NotificationCore.PILL_DATES_TAKEN_TYPE)
        val pillDatesTakenType = pillDatesTakenTypeString?.let { DatesTakenType.valueOf(it) }

        val pillDatesTakenSelectedString = inputData.getString(NotificationCore.PILL_DATES_TAKEN_SELECTED)
        val pillDatesTakenSelectedList = pillDatesTakenSelectedString?.let {
            DatesTakenSelectedListConverter().mapStringToList(it)
        } ?: listOf()

        if (endPoint != null && token != null && deviceId != null && pillStartDate != null && pillDatesTakenType != null) {
            if (dateFormatter.isShownToday(
                    pillStartDate,
                    pillEndDate,
                    LocalDate.now(),
                    pillDatesTakenType,
                    pillDatesTakenSelectedList,
                )
            ) {
                createPillNotification(
                    notificationImage,
                    pillName!!,
                    packageName!!,
                    className!!,
                )

//                notificationCore.createWorker(
//
//                )
            }
        }

        val outputData = Data.Builder().putString(NotificationCore.NOTIFICATION_DATA, "Notification data").build()

        return Result.success(outputData)
    }

    private fun createPillNotification(
        notificationImage: Int,
        pillName: String,
        packageName: String,
        className: String,
    ) {
        notificationCore.sendOnDefaultChannel(
            applicationContext,
            notificationId.getAndIncrement().toString(),
            notificationImage,
            null,
            applicationContext.getString(R.string.app_name),
            buildString {
                append(applicationContext.getString(R.string.notification_title_pill))
                append(": ")
                append(pillName)
            },
            packageName,
            className,
            "Healsted"
        )
    }

}