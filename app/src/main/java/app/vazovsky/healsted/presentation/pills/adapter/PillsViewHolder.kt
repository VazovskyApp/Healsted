package app.vazovsky.healsted.presentation.pills.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemPillBinding
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.toIcon
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.util.*

class PillsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill)) {
    private val binding by viewBinding(ItemPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.name.capitalizeFirstChar(Locale.getDefault())
        textViewDates.text = dateFormatter.formatPeriod(item.startDate, item.endDate)
        imageViewIcon.setBackgroundResource(item.type.toIcon())

        val currentProgress = item.times.count { it.value }
        val maxProgress = item.times.size

        textViewProgressTime.text = buildString {
            append(currentProgress)
            append("/")
            append(maxProgress)
        }

        progressBarTimes.progress = currentProgress
        progressBarTimes.max = maxProgress
    }
}