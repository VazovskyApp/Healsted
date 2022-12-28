package app.vazovsky.mypills.data.model

import androidx.annotation.DrawableRes

data class SettingsItem(
    val type: SettingType,
    @DrawableRes val icon: Int?,
    val group: SettingsGroup,
)