package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

/** Данные о прогрессе уровня в аккаунте  */
@Parcelize
data class LoyaltyProgress(
    @PropertyName("level") val level: AccountLevel = AccountLevel.BACTERIA,
    @PropertyName("currentValue") val currentValue: Int = 0,
) : Parcelable