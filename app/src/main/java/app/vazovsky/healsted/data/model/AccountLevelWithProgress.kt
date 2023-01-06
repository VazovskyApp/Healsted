package app.vazovsky.healsted.data.model

import com.google.firebase.firestore.PropertyName

/** Данные о прогрессе уровня в аккаунте  */
data class LoyaltyProgress(
    @PropertyName("level") val level: AccountLevel = AccountLevel.BACTERIA,
    @PropertyName("currentValue") val currentValue: Int = 0,
)