package app.vazovsky.healsted.presentation.pilleditor.datestakenselected

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.DatesTakenSelectedItem
import app.vazovsky.healsted.databinding.ItemDatesTakenSelectedBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding

class DatesTakenSelectedViewHolder(
    parent: ViewGroup,
    private val onItemClick: (DatesTakenSelectedItem) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_dates_taken_selected)) {
    private val binding by viewBinding(ItemDatesTakenSelectedBinding::bind)

    fun bind(item: DatesTakenSelectedItem) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(if (item.selected) R.color.pillsCardGreen else R.color.pillsCardBlue))
        }

        textViewDayOfWeek.text = dateFormatter.getDayOfWeekDisplay(item.value)
    }
}