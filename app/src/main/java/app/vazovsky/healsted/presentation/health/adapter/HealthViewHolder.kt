package app.vazovsky.healsted.presentation.health.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.databinding.ItemMonitoringBinding
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class HealthViewHolder(
    parent: ViewGroup,
    private val onItemClick: (MonitoringAttribute) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_monitoring)) {

    private val binding by viewBinding(ItemMonitoringBinding::bind)

    fun bind(item: MonitoringAttribute, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewTitle.text = item.type.toString()
        /** TODO Добавить форматирование */
        textViewValue.text = item.value

        /** TODO добавить картинку */
        //imageViewBackground.load()
    }
}