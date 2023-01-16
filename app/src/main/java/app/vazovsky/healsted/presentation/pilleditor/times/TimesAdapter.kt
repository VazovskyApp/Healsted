package app.vazovsky.healsted.presentation.pilleditor.times

import android.view.ViewGroup
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import java.time.LocalTime
import javax.inject.Inject

class TimesAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
) : BaseAdapter<TimeItem, TimeViewHolder>() {

    lateinit var onDeleteClick: (TimeItem) -> Unit
    lateinit var editTime: (TimeItem, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(parent, onDeleteClick, editTime, dateFormatter)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            holder.bind(getItem(position), position)
        } else {
            if (payloads[0] is LocalTime) {
                holder.bindTimeState(payloads[0] as LocalTime)
            }
        }
    }

    fun addItem(item: TimeItem) {
        this.items.apply {
            add(item)
        }
        notifyDataSetChanged()
    }

    fun updateItem(item: TimeItem, position: Int) {
        items[position] = item
        notifyItemChanged(position, item.time)
    }

    fun deleteItem(item: TimeItem) {
        this.items.apply {
            remove(item)
        }
        notifyDataSetChanged()
    }
}