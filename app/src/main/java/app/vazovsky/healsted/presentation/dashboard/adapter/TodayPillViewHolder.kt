package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemTodayPillBinding
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class TodayPillViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_today_pill)) {
    private val binding by viewBinding(ItemTodayPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.name
        // TODO формат
        textViewCount.text = item.amount.toString()
        // TODO время добавить адекватно
        textViewTime.text = item.times?.firstOrNull()?.hour.toString()
    }
}