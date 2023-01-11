package app.vazovsky.healsted.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.vazovsky.healsted.data.room.dao.PillDao.Companion.PILL_TABLE_NAME
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве */
@Parcelize
@Entity(tableName = PILL_TABLE_NAME)
data class Pill(
    /** ID лекарства */
    @PrimaryKey @ColumnInfo(name = "id") @PropertyName("id") val id: String = UUID.randomUUID().toString(),

    /** Название лекарства */
    @ColumnInfo(name = "name") @PropertyName("name") val name: String = "",

    /** Тип лекарства */
    @ColumnInfo(name = "type") @PropertyName("type") val type: PillType = PillType.CAPSULE,

    /** Тип принятия лекарства в зависимости от еды */
    @ColumnInfo(name = "takePillType") @PropertyName("takePillType") val takePillType: TakePillType? = TakePillType.NEVERMIND,

    /** Список времени уведомлений */
    @ColumnInfo(name = "times") @PropertyName("times") val times: List<Timestamp>? = listOf(
        LocalDate.now().toStartOfDayTimestamp()
    ),

    /** Регулярность приема лекарств */
    @ColumnInfo(name = "datesTaken") @PropertyName("datesTaken") val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,

    /** Выбранные дни недели для уведомлений */
    @ColumnInfo(name = "datesTakenSelected") @PropertyName("datesTakenSelected") val datesTakenSelected: List<DatesTakenSelected> = listOf(),

    /** Начальная дата принятия лекарств */
    @ColumnInfo(name = "startDate") @PropertyName("startDate") val startDate: Timestamp = LocalDate.now().toStartOfDayTimestamp(),

    /** Конечная дата принятия лекарств, если есть */
    @ColumnInfo(name = "endDate") @PropertyName("endDate") val endDate: Timestamp? = null,

    /** Дозировка лекарства */
    @ColumnInfo(name = "amount") @PropertyName("amount") val amount: Float = 1F,

    @ColumnInfo(name = "comment") @PropertyName("comment") val comment: String? = "",
) : Parcelable