package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
    @PropertyName("times") val times: Map<String, LocalTime> = mapOf(),

    /** Регулярность приема лекарств */
    @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @PropertyName("datesTakenSelected") val datesTakenSelected: ArrayList<Int> = arrayListOf(),

    /** Начальная дата принятия лекарств */
    @PropertyName("startDate") val startDate: LocalDate = LocalDate.now(),

    /** Конечная дата принятия лекарств, если есть */
    @PropertyName("endDate") val endDate: LocalDate? = null,

    /** Дозировка лекарства */
    @PropertyName("amount") val amount: Float = 1F,

    @PropertyName("comment") val comment: String = "",

    @PropertyName("history") val history: Map<LocalDateTime, LocalTime> = mutableMapOf(),
) : Parcelable