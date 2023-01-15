package app.vazovsky.healsted.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.core.core.NotificationCore.Companion.DEFAULT_ENDPOINT
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.room.converters.DatesTakenSelectedListConverter
import app.vazovsky.healsted.data.room.converters.TimesMapConverter
import app.vazovsky.healsted.extensions.toMinutes
import app.vazovsky.healsted.managers.DateFormatter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.*
import java.util.concurrent.atomic.*
import timber.log.Timber

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
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

        val accountUid = inputData.getString(NotificationCore.ACCOUNT_UID)
        val pillId = inputData.getString(NotificationCore.PILL_ID)

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
        } ?: arrayListOf()

        if (endPoint != null && token != null && deviceId != null &&
            accountUid != null && pillId != null && pillStartDate != null && pillDatesTakenType != null && pillTimes != null
        ) {
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

                createNewWorker(
                    accountUid, pillId, pillName, pillStartDate, pillEndDate,
                    pillDatesTakenType, pillDatesTakenSelectedList, pillTimes,
                    notificationImage,
                )
            }
        }

        val outputData = Data.Builder().putString(NotificationCore.NOTIFICATION_DATA, "Notification data").build()

        return Result.success(outputData)
    }

    private fun createNewWorker(
        uid: String,
        pillId: String,
        pillName: String,
        pillStartDate: LocalDate,
        pillEndDate: LocalDate?,
        pillDatesTakenType: DatesTakenType,
        pillDatesTakenSelectedList: ArrayList<Int>,
        times: Map<String, LocalTime>,
        notificationImage: Int,
    ) {
        val workManager = WorkManager.getInstance(applicationContext)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()

        val data = Data.Builder()
        data.putString(NotificationCore.ENDPOINT_REQUEST, NotificationCore.DEFAULT_ENDPOINT)
        data.putString(NotificationCore.TOKEN, NotificationCore.DEFAULT_TOKEN)
        data.putString(NotificationCore.DEVICE_ID, NotificationCore.DEFAULT_DEVICE_ID)
        data.putInt(NotificationCore.NOTIFICATION_IMAGE, notificationImage)
        data.putString(NotificationCore.PACKAGE_NAME, NotificationCore.DEFAULT_PACKAGE_NAME)
        data.putString(NotificationCore.CLASS_NAME, NotificationCore.DEFAULT_NOTIFICATION_PACKAGE_NAME)

        data.putString(NotificationCore.ACCOUNT_UID, uid)
        data.putString(NotificationCore.PILL_ID, pillId)
        data.putString(NotificationCore.PILL_NAME, pillName)
        data.putString(
            NotificationCore.PILL_TIMES,
            TimesMapConverter().mapMapToString(pillMapper.fromModelToEntityTime(times)),
        )
        data.putString(NotificationCore.PILL_START_DATE, dateFormatter.formatStringFromLocalDate(pillStartDate))
        data.putString(NotificationCore.PILL_END_DATE, pillEndDate?.let { dateFormatter.formatStringFromLocalDate(it) })
        data.putString(NotificationCore.PILL_DATES_TAKEN_TYPE, pillDatesTakenType.name)
        data.putString(
            NotificationCore.PILL_DATES_TAKEN_SELECTED,
            DatesTakenSelectedListConverter().mapListToString(pillDatesTakenSelectedList),
        )

        val nowTime = LocalTime.now()
        val soonTime = times.values.sorted().firstOrNull { it >= nowTime }
        val firstTime = times.values.minOf { it }
        val differentTime =
            soonTime?.minusMinutes(nowTime.toMinutes().toLong()) ?: LocalTime.MIDNIGHT.minusMinutes(nowTime.toMinutes().toLong())
                .plusMinutes(firstTime.toMinutes().toLong())
        val differentTimeMinutes = differentTime.toMinutes()
        Timber.d("LOL: times: ${times.values}; soonTime: $soonTime; differentTime: $differentTime")

        val work = OneTimeWorkRequestBuilder<FetchDataWorker>()
            .setConstraints(constraints)
            .addTag(NotificationCore.NOTIFICATION_WORK_MANAGER_TAG)
            .addTag(uid)
            .addTag(pillId)
            .setInputData(data.build())
            .setInitialDelay(differentTimeMinutes.toLong(), TimeUnit.MINUTES)
            .build()

        workManager.enqueue(work)
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
            DEFAULT_ENDPOINT,
        )
    }
}