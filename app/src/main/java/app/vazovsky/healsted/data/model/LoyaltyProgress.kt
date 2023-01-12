package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

/** Данные о прогрессе уровня в аккаунте  */
@Parcelize
data class LoyaltyProgress(
    /** Уровень аккаунта */
    @PropertyName("level") val level: AccountLevel = AccountLevel.BACTERIA,

    /** Текущее значение xp */
    @PropertyName("currentValue") val currentValue: Int = 0,
) : Parcelable