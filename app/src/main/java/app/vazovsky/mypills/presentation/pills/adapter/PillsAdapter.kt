package app.vazovsky.mypills.presentation.pills.adapter

import android.view.ViewGroup
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.presentation.view.BaseAdapter
import javax.inject.Inject

class PillsAdapter @Inject constructor() : BaseAdapter<Pill, PillsViewHolder>() {

    lateinit var onItemClick: (Pill) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillsViewHolder {
        return PillsViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PillsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}