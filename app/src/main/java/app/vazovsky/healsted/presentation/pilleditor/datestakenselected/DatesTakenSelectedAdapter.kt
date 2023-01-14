package app.vazovsky.healsted.presentation.pilleditor.datestakenselected

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.DatesTakenSelectedItem
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class DatesTakenSelectedAdapter @Inject constructor(
    private val dateFormatter: DateFormatter
) : BaseAdapter<DatesTakenSelectedItem, DatesTakenSelectedViewHolder>() {

    lateinit var onItemClick: (DatesTakenSelectedItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesTakenSelectedViewHolder {
        return DatesTakenSelectedViewHolder(parent, onItemClick, dateFormatter)
    }

    override fun onBindViewHolder(holder: DatesTakenSelectedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun selectItem(value: Int) {
        items.forEach {
            if(it.value == value) it.selected = !it.selected
        }
        notifyDataSetChanged()
    }

    fun getSelectedItemsValue(): ArrayList<Int> {
        val list = items.filter { it.selected }.map { it.value }.sorted()
        val array = arrayListOf<Int>()
        list.forEach {
            array.add(it)
        }
        return array
    }
}