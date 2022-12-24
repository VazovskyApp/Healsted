package app.vazovsky.mypills.presentation.health.adapter

import android.view.ViewGroup
import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.presentation.view.BaseAdapter
import javax.inject.Inject

class HealthAdapter @Inject constructor() : BaseAdapter<MonitoringAttribute, HealthViewHolder>() {

    lateinit var onItemClick: (MonitoringAttribute) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder {
        return HealthViewHolder(
            parent = parent,
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}