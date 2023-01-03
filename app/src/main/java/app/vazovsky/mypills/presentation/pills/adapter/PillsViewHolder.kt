package app.vazovsky.mypills.presentation.pills.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillsTab
import app.vazovsky.mypills.databinding.ItemPillBinding
import app.vazovsky.mypills.databinding.ItemPillsTabBinding
import app.vazovsky.mypills.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class PillsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill)) {
    private val binding by viewBinding(ItemPillBinding::bind)

    fun bind(item: Pill) = with(binding) {
        textViewName.text = item.name
        textViewDates.text = "${item.startDate} - ${item.endDate}"

        root.setOnClickListener {
            onItemClick(item)
        }
    }
}