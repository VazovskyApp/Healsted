package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import java.time.LocalTime
import javax.inject.Inject

class TodayPillsAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : BaseAdapter<Pill, TodayPillViewHolder>() {

    lateinit var onEditItemClick: (Pill) -> Unit
    lateinit var onDoneItemClick: (Pill, LocalTime) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayPillViewHolder {
        return TodayPillViewHolder(parent, onEditItemClick, onDoneItemClick, dateFormatter, dataTypeFormatter)
    }

    override fun onBindViewHolder(holder: TodayPillViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}