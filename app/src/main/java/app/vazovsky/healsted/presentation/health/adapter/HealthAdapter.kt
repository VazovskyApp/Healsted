package app.vazovsky.healsted.presentation.health.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class HealthAdapter @Inject constructor() : BaseAdapter<MonitoringAttribute, HealthViewHolder>() {

    lateinit var onItemClick: (MonitoringAttribute) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder {
        return HealthViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}