package app.vazovsky.healsted.presentation.health.attribute.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.ItemMonitoringHistoryBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding

class HealthAttributeHistoryViewHolder(
    parent: ViewGroup,
    private val onItemClick: (MonitoringAttribute) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_monitoring_history)) {

    private val binding by viewBinding(ItemMonitoringHistoryBinding::bind)

    fun bind(item: MonitoringAttribute) = with(binding) {
        root.setOnClickListener { onItemClick.invoke(item) }
        textViewValue.text = item.value
        textViewDate.text = dateFormatter.formatStandardDate(item.date)
    }
}