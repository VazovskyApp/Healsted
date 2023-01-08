package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.time.OffsetTime
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class Pill(
    /** ID лекарства */
    @PropertyName("id") val id: String = "",

    /** Название лекарства */
    @PropertyName("name") val name: String = "",

    /** Тип лекарства */
    @PropertyName("type") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @PropertyName("takePillType") val takePillType: TakePillType? = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @PropertyName("times") val times: List<OffsetTime>? = null,

    /** Регулярность приема лекарств */
    @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @PropertyName("datesTakenSelected") val datesTakenSelected: List<DatesTakenSelected> = listOf(),

    /** Начальная дата принятия лекарств */
    @PropertyName("startDate") val startDate: Timestamp = Timestamp.now(),

    /** Конечная дата принятия лекарств, если есть */
    @PropertyName("endDate") val endDate: Timestamp? = null,

    /** Дозировка лекарства */
    @PropertyName("amount") val amount: Float = 1F,
) : Parcelable