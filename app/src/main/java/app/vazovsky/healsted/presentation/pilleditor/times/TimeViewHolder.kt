package app.vazovsky.healsted.presentation.pilleditor.times

import android.text.Editable
import android.text.TextWatcher
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
    private val onDeleteClick: (TimeItem) -> Unit,
    private val editTime: (TimeItem, Int) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_time)) {
    private val binding by viewBinding(ItemTimeBinding::bind)

    private var item: TimeItem? = null

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) = with(binding) {
            if (textInputTime.validate()) {
                val newItem = TimeItem(item?.id.orDefault(), dateFormatter.parseLocalTimeFromString(editTextTime.text.toString()))
                editTime.invoke(newItem, position)
            }
        }
    }

    fun bind(item: TimeItem, position: Int) = with(binding) {
        this@TimeViewHolder.item = item
        buttonDelete.apply {
            visibility = if (position != 0) VISIBLE else INVISIBLE
            setOnClickListener { onDeleteClick.invoke(item) }
        }
        editTextTime.setText(dateFormatter.formatStringFromLocalTime(item.time))
        editTextTime.addTextChangedListener(watcher)
    }

    fun bindTimeState(time: LocalTime) = with(binding) {
        item = TimeItem(item?.id.orDefault(), time)
        editTextTime.removeTextChangedListener(watcher)
        editTextTime.setText(dateFormatter.formatStringFromLocalTime(time))
    }
}