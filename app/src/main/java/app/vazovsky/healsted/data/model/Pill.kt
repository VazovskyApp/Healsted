package app.vazovsky.healsted.data.model

import android.os.Parcelable
import java.time.OffsetDateTime
import java.time.OffsetTime
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class Pill(
    /** ID лекарства */
    val id: String,

    /** Название лекарства */
    val name: String = "",

    /** Тип лекарства */
    val type: PillType,

    /** Тип принятия лекарства в зависимости от еды */
    val takePillType: TakePillType? = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    val times: List<OffsetTime>? = null,

    /** Регулярность приема лекарств */
    val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    val datesTakenSelected: List<DatesTakenSelected> = listOf(),

    /** Начальная дата принятия лекарств */
    val startDate: OffsetDateTime,

    /** Конечная дата принятия лекарств, если есть */
    val endDate: OffsetDateTime? = null,

    /** Дозировка лекарства */
    val amount: Float = 1F,
) : Parcelable