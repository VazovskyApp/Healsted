package app.vazovsky.healsted.presentation.dashboard.adapter

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class TodayPillsAdapter @Inject constructor() : BaseAdapter<Pill, TodayPillViewHolder>() {

    lateinit var onItemClick: (Pill) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayPillViewHolder {
        return TodayPillViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: TodayPillViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}