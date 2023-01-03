package app.vazovsky.mypills.presentation.health.adapter

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

    fun bind(item: MonitoringAttribute, position: Int) = with(binding) {
        root.setOnClickListener { onItemClick.invoke(item) }
        val backgroundColorId = when (position % 5) {
            0 -> R.color.pillsCardBlue
            1 -> R.color.pillsCardOrange
            2 -> R.color.pillsCardRed
            3 -> R.color.pillsCardViolet
            else -> R.color.pillsCardGreen
        }
        root.setCardBackgroundColor(
            root.context.getColor(backgroundColorId)
        )

        textViewTitle.text = item.type.toString()
        /** Добавить форматирование */
        textViewValue.text = item.value

        //imageViewBackground.load()
    }
}