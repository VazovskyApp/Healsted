package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.extensions.toDefaultString
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class PillDocument(
    /** ID лекарства */
    @SerializedName("id") @PropertyName("id") val id: String = UUID.randomUUID().toString(),

    /** Название лекарства */
    @SerializedName("name") @PropertyName("name") val name: String = "",

    /** Тип лекарства */
    @SerializedName("type") @PropertyName("type") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @SerializedName("takePillType") @PropertyName("takePillType") val takePillType: TakePillType = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @SerializedName("times") @PropertyName("times") val times: Map<String, String> = mapOf(),

    /** Регулярность приема лекарств */
    @SerializedName("datesTaken") @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @SerializedName("datesTakenSelected") @PropertyName("datesTakenSelected") val datesTakenSelected: ArrayList<Int> = arrayListOf(),

    /** Начальная дата принятия лекарств */
    @SerializedName("startDate") @PropertyName("startDate") val startDate: String = LocalDate.now().toDefaultString(),

    /** Конечная дата принятия лекарств, если есть */
    @SerializedName("endDate") @PropertyName("endDate") val endDate: String? = null,

    /** Дозировка лекарства */
    @SerializedName("amount") @PropertyName("amount") val amount: Float = 1F,

    @SerializedName("comment") @PropertyName("comment") val comment: String = "",

    @SerializedName("history") @PropertyName("history") val history: Map<String, String> = mapOf(),
) : Parcelable