package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.databinding.ItemTodayPillBinding
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.getDrawableCompat
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.toIcon
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.time.LocalTime
import java.util.*

class TodayPillViewHolder(
    parent: ViewGroup,
    private val onEditItemClick: (Pill) -> Unit,
    private val onDoneItemClick: (Pill, LocalTime) -> Unit,
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_today_pill)) {
    private val binding by viewBinding(ItemTodayPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnLongClickListener {
                onEditItemClick.invoke(item)
                true
            }

            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.name.capitalizeFirstChar(Locale.getDefault())
        textViewCount.text = dataTypeFormatter.formatPill(item)

        val minTime = item.times.maxOf { it.key }
        val isMinTimeDone = item.times[minTime] ?: false
        textViewTime.text = dateFormatter.formatStringFromLocalTime(minTime).orDefault()

        imageViewIcon.setBackgroundResource(item.type.toIcon())
        textViewCompleted.isVisible = isMinTimeDone
        imageButtonDone.apply {
            setOnClickListener { onDoneItemClick.invoke(item, minTime) }
            background = root.context.getDrawableCompat(if (isMinTimeDone) R.drawable.ic_close else R.drawable.ic_completed)
        }
    }
}