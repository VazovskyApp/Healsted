package app.vazovsky.healsted.presentation.view

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val items = mutableListOf<T>()

    override fun getItemCount() = items.size

    fun getItem(position: Int): T {
        return items[position]
    }

    fun submitList(items: List<T>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    val currentList: List<T>
        get() = items
}