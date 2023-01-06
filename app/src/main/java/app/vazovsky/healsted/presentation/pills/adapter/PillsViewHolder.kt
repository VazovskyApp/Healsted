package app.vazovsky.healsted.presentation.pills.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemPillBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding

class PillsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill)) {
    private val binding by viewBinding(ItemPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        /** TODO ext random color */
        val backgroundColorId = when (position % 5) {
            0 -> R.color.pillsCardBlue
            1 -> R.color.pillsCardOrange
            2 -> R.color.pillsCardRed
            3 -> R.color.pillsCardViolet
            else -> R.color.pillsCardGreen
        }
        root.apply {
            setCardBackgroundColor(context.getColor(backgroundColorId))
            setOnClickListener { onItemClick(item) }
        }

        textViewName.text = item.name
        textViewDates.text = dateFormatter.formatPeriod(item.startDate, item.endDate)
    }
}