package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.TodayPillItem
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TodayPillViewHolder(
    parent: ViewGroup,
    private val onEditItemClick: (TodayPillItem) -> Unit,
    private val onDoneItemClick: (TodayPillItem) -> Unit,
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_today_pill)) {
    private val binding by viewBinding(ItemTodayPillBinding::bind)

    fun bind(item: TodayPillItem, position: Int) = with(binding) {
        root.apply {
            setOnLongClickListener {
                onEditItemClick.invoke(item)
                true
            }

            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.pill.name.capitalizeFirstChar(Locale.getDefault())
        textViewCount.text = dataTypeFormatter.formatPill(item.pill)
        textViewTime.text = dateFormatter.formatStringFromLocalTime(item.time).orDefault()

        val localDateTime = LocalDateTime.of(item.date, item.time)
        val isHistoryContainedLocalDateTime = item.pill.history[localDateTime]
        val isDone = isHistoryContainedLocalDateTime != null

        imageViewIcon.setBackgroundResource(item.pill.type.toIcon())
        textViewCompleted.isVisible = isDone
        imageButtonDone.apply {
            isVisible = LocalDate.now().atStartOfDay() >= item.date.atStartOfDay()
            setOnClickListener { onDoneItemClick.invoke(item) }
            background = root.context.getDrawableCompat(if (isDone) R.drawable.ic_close else R.drawable.ic_completed)
        }
    }
}