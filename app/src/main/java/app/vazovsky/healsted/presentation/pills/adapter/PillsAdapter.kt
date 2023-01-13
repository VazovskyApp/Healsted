package app.vazovsky.healsted.presentation.pills.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class PillsAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : BaseAdapter<Pill, PillsViewHolder>() {

    lateinit var onItemClick: (Pill) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillsViewHolder {
        return PillsViewHolder(parent, onItemClick, dateFormatter, dataTypeFormatter)
    }

    override fun onBindViewHolder(holder: PillsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}