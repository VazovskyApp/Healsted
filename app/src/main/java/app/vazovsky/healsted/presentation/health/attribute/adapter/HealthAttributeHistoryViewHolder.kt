package app.vazovsky.healsted.presentation.health.attribute.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.ItemMonitoringHistoryBinding
import app.vazovsky.healsted.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class HealthAttributeHistoryViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_monitoring_history)) {

    private val binding by viewBinding(ItemMonitoringHistoryBinding::bind)

    fun bind(item: MonitoringAttribute) = with(binding) {
        textViewValue.text = item.value
        //TODO форматирование
        textViewDate.text = item.date.toString()
    }
}