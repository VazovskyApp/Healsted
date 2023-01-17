package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.extensions.withZeroSecondsAndNano
import app.vazovsky.healsted.managers.DataTypeFormatter
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.view.BaseAdapter
import java.time.LocalTime
import javax.inject.Inject

class TodayPillsAdapter @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val dataTypeFormatter: DataTypeFormatter,
) : BaseAdapter<TodayPillItem, TodayPillViewHolder>() {

    lateinit var onEditItemClick: (TodayPillItem) -> Unit
    lateinit var onDoneItemClick: (TodayPillItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayPillViewHolder {
        return TodayPillViewHolder(parent, onEditItemClick, onDoneItemClick, dateFormatter, dataTypeFormatter)
    }

    override fun onBindViewHolder(holder: TodayPillViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun getSoonTimePosition(): Int {
        val localTime = LocalTime.now().withZeroSecondsAndNano().plusHours(6)
        return items.indexOfFirst{ it.time >= localTime }
    }
}