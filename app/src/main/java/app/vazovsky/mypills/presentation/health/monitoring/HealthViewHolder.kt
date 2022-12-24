package app.vazovsky.mypills.presentation.health.monitoring

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.MonitoringAttribute
import app.vazovsky.mypills.databinding.ItemMonitoringBinding
import app.vazovsky.mypills.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class HealthViewHolder(
    parent: ViewGroup,
    private val onItemClick: (MonitoringAttribute) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_monitoring)) {

    private val binding by viewBinding(ItemMonitoringBinding::bind)

    fun bind(item: MonitoringAttribute) = with(binding) {
        textViewTitle.text = item.title
        /** Добавить форматирование */
        textViewValue.text = item.value
        root.setOnClickListener { onItemClick.invoke(item) }
        //imageViewBackground.load()
    }
}