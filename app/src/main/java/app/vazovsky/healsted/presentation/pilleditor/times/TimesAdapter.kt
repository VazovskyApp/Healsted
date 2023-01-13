package app.vazovsky.healsted.presentation.pilleditor.times

import android.view.ViewGroup
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import java.time.LocalTime
import javax.inject.Inject

class TimesAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
) : BaseAdapter<Pair<LocalTime, Boolean>, TimeViewHolder>() {

    lateinit var onDeleteClick: (Pair<LocalTime, Boolean>) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(parent, onDeleteClick, dateFormatter)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun addItem(item: Pair<LocalTime, Boolean>) {
        this.items.apply {
            add(item)
        }
        notifyDataSetChanged()
    }

    fun deleteItem(item: Pair<LocalTime, Boolean>) {
        this.items.apply {
            remove(item)
        }
        notifyDataSetChanged()
    }
}