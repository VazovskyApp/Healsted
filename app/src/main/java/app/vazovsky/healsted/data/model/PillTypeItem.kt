package app.vazovsky.healsted.data.model

import androidx.annotation.DrawableRes

/** Пункт типов лекарств */
data class PillTypeItem(
    /** Тип лекарств */
    val pillType: PillType,

    /** Иконка */
    @DrawableRes val icon: Int?,

    /** Выбран ли тип */
    var selected: Boolean = false,
)