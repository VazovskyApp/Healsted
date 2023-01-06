package app.vazovsky.healsted.presentation.health.attribute.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

/** TODO подумать, будет ли возможность нажатия и редактирования данных из истории */
class HealthAttributeHistoryAdapter @Inject constructor() : BaseAdapter<MonitoringAttribute, HealthAttributeHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAttributeHistoryViewHolder {
        return HealthAttributeHistoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HealthAttributeHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}