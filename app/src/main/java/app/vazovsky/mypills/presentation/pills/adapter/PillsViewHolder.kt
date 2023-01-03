package app.vazovsky.mypills.presentation.pills.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.databinding.ItemPillBinding
import app.vazovsky.mypills.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class PillsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill)) {
    private val binding by viewBinding(ItemPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        textViewName.text = item.name
        textViewDates.text = "${item.startDate} - ${item.endDate}"
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
        root.setOnClickListener {
            onItemClick(item)
        }
    }
}