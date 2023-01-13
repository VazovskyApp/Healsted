package app.vazovsky.healsted.presentation.pilleditor.times

import android.view.ViewGroup
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import com.google.firebase.Timestamp
import javax.inject.Inject

class TimesAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
) : BaseAdapter<Timestamp, TimeViewHolder>() {

    lateinit var onDeleteClick: (Timestamp) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(parent, onDeleteClick, dateFormatter)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun addItem(item: Timestamp) {
        this.items.apply {
            add(item)
        }
        notifyDataSetChanged()
    }

    fun deleteItem(item: Timestamp) {
        this.items.apply {
            remove(item)
        }
        notifyDataSetChanged()
    }
}