package app.vazovsky.healsted.core.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.vazovsky.healsted.core.viewmodel.NotificationViewModel
import app.vazovsky.healsted.core.worker.FetchDataWorker
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.room.converters.DatesTakenSelectedListConverter
import app.vazovsky.healsted.data.room.converters.TimesMapConverter
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.toMinutes
import app.vazovsky.healsted.extensions.withZeroSecondsAndNano
import app.vazovsky.healsted.managers.DateFormatter
import com.google.gson.JsonObject
import java.time.LocalTime
import java.util.concurrent.*
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class NotificationCore @Inject constructor(
    private val pillMapper: PillMapper,
    private val dateFormatter: DateFormatter,
) {

    private var notificationViewModel: NotificationViewModel? = null

    companion object {
        const val DEFAULT_TOKEN = "**"
        const val DEFAULT_ENDPOINT = "Healsted"
        const val DEFAULT_DEVICE_ID = "test"
        const val DEFAULT_PACKAGE_NAME = "app.vazovsky.healsted"
        const val DEFAULT_NOTIFICATION_PACKAGE_NAME = "app.vazovsky.healsted.presentation.mainscreen.MainActivity"
        const val FULL_DAY_MINUTES = 1440

        const val CHANNEL_ID_DEFAULT: String = "CHANNEL_ID_DEFAULT"
        const val NOTIFICATION_DATA: String = "NOTIFICATION_DATA"
        const val ENDPOINT_REQUEST: String = "ENDPOINT_REQUEST"
        const val TOKEN: String = "TOKEN"
        const val DEVICE_ID: String = "DEVICE_ID"
        const val NOTIFICATION_EXTRA: String = "NOTIFICATION_EXTRA"
        const val NOTIFICATION_ID: String = "NOTIFICATION_ID"
        const val NOTIFICATION_CLICK_ENDPOINT: String = "NOTIFICATION_CLICK_ENDPOINT"
        const val NOTIFICATION_CLICK_DATA_EXTRA: String = "NOTIFICATION_CLICK_EXTRA"
        const val PACKAGE_NAME: String = "PACKAGE_NAME"
        const val CLASS_NAME: String = "CLASS_NAME"
        const val NOTIFICATION_WORK_MANAGER_TAG: String = "NOTIFICATION_WORK_MANAGER_TAG"
        const val ACCOUNT_UID: String = "ACCOUNT_UID"
        const val PILL_ID = "PILL_ID"
        const val PILL_NAME = "PILL_NAME"
        const val PILL_AMOUNT = "PILL_AMOUNT"
        const val PILL_TYPE = "PILL_TYPE"
        const val PILL_TIMES = "PILL_TIMES"
        const val PILL_START_DATE = "PILL_START_DATE"
        const val PILL_END_DATE = "PILL_END_DATE"
        const val PILL_DATES_TAKEN_TYPE = "PILL_DATES_TAKEN_TYPE"
        const val PILL_DATES_TAKEN_SELECTED = "PILL_DATES_TAKEN_SELECTED"
    }

    fun init(
        application: Application,
        owner: ViewModelStoreOwner,
    ) {
        createNotificationDefaultChannel(application)
        notificationViewModel = ViewModelProvider(owner)[NotificationViewModel::class.java]
    }

    /** ?????????????? ???????????? */
    fun createWorker(
        application: Application,
        token: String = DEFAULT_TOKEN,
        endPoint: String = DEFAULT_ENDPOINT,
        deviceId: String = DEFAULT_DEVICE_ID,
        notificationImage: Int,
        notificationPackageName: String = DEFAULT_PACKAGE_NAME,
        notificationClassPackageName: String = DEFAULT_NOTIFICATION_PACKAGE_NAME,
        uid: String,
        pill: Pill,
    ) {
        val workManager = WorkManager.getInstance(application.applicationContext!!)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()

        val data = Data.Builder()
        data.putString(ENDPOINT_REQUEST, endPoint)
        data.putString(TOKEN, token)
        data.putString(DEVICE_ID, deviceId)
        data.putString(PACKAGE_NAME, notificationPackageName)
        data.putString(CLASS_NAME, notificationClassPackageName)
        data.putString(PILL_NAME, pill.name)
        data.putFloat(PILL_AMOUNT, pill.amount)
        data.putString(PILL_TYPE, pill.type.name)
        data.putString(PILL_TIMES, TimesMapConverter().mapMapToString(pillMapper.fromModelToEntityTime(pill.times)))
        data.putString(PILL_START_DATE, dateFormatter.formatStringFromLocalDate(pill.startDate))
        data.putString(PILL_END_DATE, pill.endDate?.let { dateFormatter.formatStringFromLocalDate(it) })
        data.putString(PILL_DATES_TAKEN_TYPE, pill.datesTaken.name)
        data.putString(PILL_DATES_TAKEN_SELECTED, DatesTakenSelectedListConverter().mapListToString(pill.datesTakenSelected))
        data.putString(ACCOUNT_UID, uid)
        data.putString(PILL_ID, pill.id)

        /** ???????????? ?????????????? ???????????? */
        val nowTimeWithSeconds = LocalTime.now()
        val nowTime = nowTimeWithSeconds.withZeroSecondsAndNano()

        /** ?????????????????? ?????????? ?? ???????????? */
        val soonTime = pill.times.values.sorted().firstOrNull { itemTime ->
            itemTime >= nowTime && itemTime.withZeroSecondsAndNano() != nowTime.withZeroSecondsAndNano()
        }?.withZeroSecondsAndNano()

        /** ?????????????????????? ?????????? ?? ???????????? */
        val firstTime = pill.times.values.minOf { it }

        val differentTime = if (soonTime == null) {
            /** ?????????????? */
            val midnight = LocalTime.MIDNIGHT

            /** ?????????? ???? ???????????????? */
            val minutesUntilMidnight = midnight.minusMinutes(nowTime.toMinutes().toLong())

            /** ?????????? ???? ???????????? ?????? ???? ?????????????? ?????????????? */
            val minutesUntilCurrentTime = firstTime.toMinutes().toLong()
            minutesUntilMidnight.plusMinutes(minutesUntilCurrentTime)
        } else {
            soonTime.minusMinutes(nowTime.toMinutes().toLong())
        }
        var differentTimeMinutes = differentTime.toMinutes()
        if (differentTimeMinutes == 0) {
            differentTimeMinutes = FULL_DAY_MINUTES
        }
        val differentTimeSecond = differentTimeMinutes * 60 - nowTimeWithSeconds.second
        Timber.d(
            "LOL: times: ${pill.times.values}; soonTime: $soonTime; " +
                    "minutes: $differentTimeMinutes; seconds: $differentTimeSecond"
        )

        val work = OneTimeWorkRequestBuilder<FetchDataWorker>()
            .setConstraints(constraints)
            .addTag(NOTIFICATION_WORK_MANAGER_TAG)
            .addTag(uid)
            .addTag(pill.id)
            .setInputData(data.build())
            .setInitialDelay(differentTimeSecond.toLong(), TimeUnit.SECONDS)
            .build()

        workManager.enqueue(work)
    }

    /** ?????????????????? ???????????? ?????????????? */
    fun cancelWorker(
        application: Application,
        tag: String,
    ) {
        WorkManager.getInstance(application.applicationContext!!).cancelAllWorkByTag(tag)
    }

    /** ???????????????? ???????????????????????? ???????????? ?????????????????????? */
    private fun createNotificationDefaultChannel(
        application: Application,
    ) {
        application.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID_DEFAULT, "?????????????????????? ?? ????????????????????", importance).apply {
                    description = "?????????????????? ?????????????????????? ?? ????????????????????"
                }
                val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    /** ?????????????????? ?????????????????????? ?? ???????????? ???? ?????????????????? */
    fun sendOnDefaultChannel(
        context: Context,
        notificationId: String,
        notificationImage: Int,
        data: JsonObject?,
        notificationTitle: String,
        notificationContent: String,
        notificationPackageName: String,
        notificationClassPackageName: String,
        clickReferrerEndPoint: String,
    ) {
        val componentName = ComponentName(notificationPackageName, notificationClassPackageName)
        val intent = Intent()
        intent.component = componentName
        intent.putExtra(NOTIFICATION_EXTRA, true)
        intent.putExtra(NOTIFICATION_ID, notificationId)
        intent.putExtra(NOTIFICATION_CLICK_ENDPOINT, clickReferrerEndPoint)
        intent.putExtra(NOTIFICATION_CLICK_DATA_EXTRA, data.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat
            .Builder(context, CHANNEL_ID_DEFAULT)
            .setSmallIcon(notificationImage)
            .setColorized(true)
            .setColor(context.getColor(notificationId.toInt().getColorIdFromPosition()))
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId.toInt(), builder.build())
        }
    }

    /** ?????????????? ???? ?????????????????????? */
    fun clickedOnNotification(
        endPoint: String,
        token: String,
        id: String,
    ) = notificationViewModel?.clickedOnNotification(endPoint, token, id)
}