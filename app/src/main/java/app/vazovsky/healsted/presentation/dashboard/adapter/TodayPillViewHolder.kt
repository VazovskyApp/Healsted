package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemTodayPillBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.orDefault
import by.kirich1409.viewbindingdelegate.viewBinding

class TodayPillViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_today_pill)) {
    private val binding by viewBinding(ItemTodayPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            /** TODO сделать ext типа рандомный цвет */
            val backgroundColorId = when (position % 5) {
                0 -> R.color.pillsCardBlue
                1 -> R.color.pillsCardOrange
                2 -> R.color.pillsCardRed
                3 -> R.color.pillsCardViolet
                else -> R.color.pillsCardGreen
            }
            setCardBackgroundColor(
                context.getColor(backgroundColorId)
            )
        }

        textViewName.text = item.name
        // TODO формат
        textViewCount.text = item.amount.toString()
        // TODO время добавить адекватно
        textViewTime.text = item.times?.firstOrNull()?.hour.toString()
    }
}