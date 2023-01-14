package app.vazovsky.healsted.presentation.pilleditor.times

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.ItemTimeBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.time.LocalTime

class TimeViewHolder(
    parent: ViewGroup,
    private val onDeleteClick: (Pair<String, LocalTime>) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_time)) {
    private val binding by viewBinding(ItemTimeBinding::bind)

    fun bind(item: Pair<String, LocalTime>, position: Int) = with(binding) {
        buttonDelete.apply {
            visibility = if (position != 0) VISIBLE else INVISIBLE
            setOnClickListener { onDeleteClick.invoke(item) }
        }
        editTextTime.setText(dateFormatter.formatStringFromLocalTime(item.second))
    }
}