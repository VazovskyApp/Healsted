package app.vazovsky.mypills.data.model

import androidx.annotation.DrawableRes

data class SettingsItem(
    val id: String,
    val title: String,
    val type: SettingType,
    @DrawableRes val icon: Int?,
    val group: SettingsGroup,
)