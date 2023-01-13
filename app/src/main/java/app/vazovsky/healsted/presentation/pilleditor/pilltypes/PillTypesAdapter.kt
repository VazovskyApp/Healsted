package app.vazovsky.healsted.presentation.pilleditor.pilltypes

import android.view.ViewGroup
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.presentation.view.BaseAdapter
import javax.inject.Inject

class PillTypesAdapter @Inject constructor() : BaseAdapter<PillTypeItem, PillTypeViewHolder>() {

    lateinit var onItemClick: (PillTypeItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillTypeViewHolder {
        return PillTypeViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PillTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun selectItem(type: PillType) {
        items.forEach {
            it.selected = it.pillType == type
        }
        notifyDataSetChanged()
    }

    fun getSelectedItemType() = items.first { it.selected }.pillType
}