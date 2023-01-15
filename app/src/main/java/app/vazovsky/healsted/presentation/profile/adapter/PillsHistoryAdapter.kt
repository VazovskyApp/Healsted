package app.vazovsky.healsted.presentation.profile.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class PillsHistoryAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : BaseAdapter<TodayPillItem, PillsHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillsHistoryViewHolder {
        return PillsHistoryViewHolder(parent, dateFormatter, dataTypeFormatter)
    }

    override fun onBindViewHolder(holder: PillsHistoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}