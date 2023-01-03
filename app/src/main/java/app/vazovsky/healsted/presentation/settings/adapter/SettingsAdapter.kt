package app.vazovsky.healsted.presentation.settings.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.SettingsItem
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class SettingsAdapter @Inject constructor() : BaseAdapter<SettingsItem, SettingsViewHolder>() {

    lateinit var onItemClick: (SettingsItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            parent = parent,
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}