package app.vazovsky.mypills.presentation.pills.tab.active.adapter

import android.view.ViewGroup
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.presentation.view.BaseAdapter
import javax.inject.Inject

class ActivePillsAdapter @Inject constructor() : BaseAdapter<Pill, ActivePillsViewHolder>() {

    lateinit var onItemClick: (Pill) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivePillsViewHolder {
        return ActivePillsViewHolder(
            parent = parent,
            onItemClick = onItemClick,
        )
    }

    override fun onBindViewHolder(holder: ActivePillsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}