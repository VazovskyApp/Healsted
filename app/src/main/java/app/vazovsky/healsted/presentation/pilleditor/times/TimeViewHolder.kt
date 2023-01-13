package app.vazovsky.healsted.presentation.pilleditor.times

import android.text.TextWatcher
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.ItemTimeBinding
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.Timestamp

class TimeViewHolder(
    parent: ViewGroup,
    private val onDeleteClick: (Timestamp) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_time)) {
    private val binding by viewBinding(ItemTimeBinding::bind)

    fun bind(item: Timestamp, position: Int) = with(binding) {
        buttonDelete.apply {
            visibility = if (position != 0) VISIBLE else INVISIBLE
            setOnClickListener { onDeleteClick.invoke(item) }
        }

        editTextTime.setText(dateFormatter.formatTime(item))
    }
}