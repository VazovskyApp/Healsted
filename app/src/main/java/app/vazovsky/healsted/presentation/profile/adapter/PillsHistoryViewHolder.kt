package app.vazovsky.healsted.presentation.profile.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.databinding.ItemPillsHistoryBinding
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.toIcon
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.util.*

class PillsHistoryViewHolder(
    parent: ViewGroup,
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pills_history)) {
    private val binding by viewBinding(ItemPillsHistoryBinding::bind)

    fun bind(item: TodayPillItem, position: Int) = with(binding) {
        root.apply {
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.pill.name.capitalizeFirstChar(Locale.getDefault())
        textViewCount.text = dataTypeFormatter.formatPill(item.pill)
        textViewTime.text = dateFormatter.formatStringFromLocalTime(item.time).orDefault()
        textViewDate.text = dateFormatter.formatStringFromLocalDate(item.date).orDefault()

        imageViewIcon.setBackgroundResource(item.pill.type.toIcon())
    }
}