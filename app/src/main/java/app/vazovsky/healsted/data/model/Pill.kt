package app.vazovsky.healsted.data.model

import android.os.Parcelable
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class Pill(
    /** ID лекарства */
    @PropertyName("id") val id: String = UUID.randomUUID().toString(),

    /** Название лекарства */
    @PropertyName("name") val name: String = "",

    /** Тип лекарства */
    @PropertyName("type") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @PropertyName("takePillType") val takePillType: TakePillType = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @PropertyName("times") val times: List<Timestamp> = listOf(LocalDate.now().toStartOfDayTimestamp()),

    /** Регулярность приема лекарств */
    @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @PropertyName("datesTakenSelected") val datesTakenSelected: List<DatesTakenSelected> = listOf(),

    /** Начальная дата принятия лекарств */
    @PropertyName("startDate") val startDate: Timestamp = LocalDate.now().toStartOfDayTimestamp(),

    /** Конечная дата принятия лекарств, если есть */
    @PropertyName("endDate") val endDate: Timestamp? = null,

    /** Дозировка лекарства */
    @PropertyName("amount") val amount: Float = 1F,

    @PropertyName("comment") val comment: String = "",
) : Parcelable