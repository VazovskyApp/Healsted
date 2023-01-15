package app.vazovsky.healsted.data.model

import androidx.annotation.DrawableRes

/** Пункт настроек */
data class SettingsItem(
    /** Тип настроек */
    val type: SettingType,

    /** Иконка */
    @DrawableRes val icon: Int?,
)