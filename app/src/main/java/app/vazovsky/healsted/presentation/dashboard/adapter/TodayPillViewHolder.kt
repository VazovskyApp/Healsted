package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemTodayPillBinding
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.util.*

class TodayPillViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_today_pill)) {
    private val binding by viewBinding(ItemTodayPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.name.capitalizeFirstChar(Locale.getDefault())
        textViewCount.text = dataTypeFormatter.formatPill(item)
        textViewTime.text = item.times.keys.firstOrNull()?.let { dateFormatter.formatStringFromLocalTime(it) }.orDefault()
    }
}