package app.vazovsky.healsted.presentation.pilleditor.times

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.ItemTimeBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.time.LocalTime

class TimeViewHolder(
    parent: ViewGroup,
    private val onEditClick: (TimeItem, Int) -> Unit,
    private val onAddClick: (TimeItem) -> Unit,
    private val onDeleteClick: (TimeItem) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_time)) {
    private val binding by viewBinding(ItemTimeBinding::bind)

    private var item: TimeItem? = null

    fun bind(item: TimeItem, isLastPosition: Boolean) = with(binding) {
        this@TimeViewHolder.item = item
        textViewTime.setOnClickListener {
            onEditClick.invoke(item, bindingAdapterPosition)
        }
        buttonDelete.apply {
            buttonDelete.visibility = if (isLastPosition) INVISIBLE else VISIBLE
            setOnClickListener { onDeleteClick.invoke(item) }
        }
        buttonAdd.apply {
            visibility = if (isLastPosition) VISIBLE else INVISIBLE
            setOnClickListener { onAddClick.invoke(item) }
        }
        textViewTime.text = dateFormatter.formatStringFromLocalTime(item.time)
    }

    fun bindTimeState(time: LocalTime) = with(binding) {
        item = TimeItem(item?.id.orDefault(), time)
        textViewTime.text = dateFormatter.formatStringFromLocalTime(time)
    }
}