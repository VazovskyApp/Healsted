package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.extensions.toDefaultString
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
data class PillDocument(
    /** ID лекарства */
    @PropertyName("id") val id: String = UUID.randomUUID().toString(),

    /** Название лекарства */
    @PropertyName("name") val name: String = "",

    /** Тип лекарства */
    @PropertyName("type") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @PropertyName("takePillType") val takePillType: TakePillType = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @PropertyName("times") val times: Map<String, String> = mapOf(),

    /** Регулярность приема лекарств */
    @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @PropertyName("datesTakenSelected") val datesTakenSelected: ArrayList<Int> = arrayListOf(),

    /** Начальная дата принятия лекарств */
    @PropertyName("startDate") val startDate: String = LocalDate.now().toDefaultString(),

    /** Конечная дата принятия лекарств, если есть */
    @PropertyName("endDate") val endDate: String? = null,

    /** Дозировка лекарства */
    @PropertyName("amount") val amount: Float = 1F,

    @PropertyName("comment") val comment: String = "",

    @PropertyName("history") val history: Map<String, String> = mapOf(),
) : Parcelable