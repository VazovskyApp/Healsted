package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class Pill(
    /** ID лекарства */
    @SerializedName("id") val id: String = UUID.randomUUID().toString(),

    /** Название лекарства */
    @SerializedName("name") val name: String = "",

    /** Тип лекарства */
    @SerializedName("name") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @SerializedName("takePillType") val takePillType: TakePillType = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @SerializedName("times") val times: Map<String, LocalTime> = mapOf(),

    /** Регулярность приема лекарств */
    @SerializedName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @SerializedName("datesTakenSelected") val datesTakenSelected: ArrayList<Int> = arrayListOf(),

    /** Начальная дата принятия лекарств */
    @SerializedName("startDate") val startDate: LocalDate = LocalDate.now(),

    /** Конечная дата принятия лекарств, если есть */
    @SerializedName("endDate") val endDate: LocalDate? = null,

    /** Дозировка лекарства */
    @SerializedName("amount") val amount: Float = 1F,

    @SerializedName("comment") val comment: String = "",

    @SerializedName("history") val history: Map<LocalDateTime, LocalTime> = mutableMapOf(),
) : Parcelable