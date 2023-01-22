package app.vazovsky.healsted.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.data.room.dao.PillDao
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/** Данные о лекарстве в виде Entity */
@Parcelize
@Entity(tableName = PillDao.PILL_TABLE_NAME)
data class PillEntity(
    /** ID лекарства */
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: String,

    /** Название лекарства */
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,

    /** Тип лекарства */
    @ColumnInfo(name = "type") @SerializedName("type") val type: PillType,

    /** Тип принятия лекарства в зависимости от еды */
    @ColumnInfo(name = "takePillType") @SerializedName("takePillType") val takePillType: TakePillType,

    /** Список времени уведомлений */
    @ColumnInfo(name = "times") @SerializedName("times") val times: Map<String, String>,

    /** Регулярность приема лекарств */
    @ColumnInfo(name = "datesTaken") @SerializedName("datesTaken") val datesTaken: DatesTakenType,

    /** Выбранные дни недели для уведомлений */
    @ColumnInfo(name = "datesTakenSelected") @SerializedName("datesTakenSelected") val datesTakenSelected: ArrayList<Int>,

    /** Начальная дата принятия лекарств */
    @ColumnInfo(name = "startDate") @SerializedName("startDate") val startDate: String,

    /** Конечная дата принятия лекарств, если есть */
    @ColumnInfo(name = "endDate") @SerializedName("endDate") val endDate: String?,

    /** Дозировка лекарства */
    @ColumnInfo(name = "amount") @SerializedName("amount") val amount: Float = 1F,

    @ColumnInfo(name = "comment") @SerializedName("comment") val comment: String = "",

    @ColumnInfo(name = "history") @SerializedName("history") val history: Map<String, String>,
) : Parcelable