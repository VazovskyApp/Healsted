package app.vazovsky.healsted.presentation.health.attribute.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

/** TODO подумать, будет ли возможность нажатия и редактирования данных из истории */
class HealthAttributeHistoryAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
) : BaseAdapter<MonitoringAttribute, HealthAttributeHistoryViewHolder>() {

    lateinit var onItemClick: (MonitoringAttribute) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAttributeHistoryViewHolder {
        return HealthAttributeHistoryViewHolder(parent, onItemClick, dateFormatter)
    }

    override fun onBindViewHolder(holder: HealthAttributeHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}