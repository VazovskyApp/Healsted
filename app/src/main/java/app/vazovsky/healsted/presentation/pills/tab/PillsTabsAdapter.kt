package app.vazovsky.healsted.presentation.pills.tab

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class PillsTabsAdapter @Inject constructor() : BaseAdapter<PillsTab, PillsTabViewHolder>() {

    lateinit var onItemClick: (PillsTab) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillsTabViewHolder {
        return PillsTabViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PillsTabViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
