package app.vazovsky.mypills.presentation.pills.tab

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.PillsTab
import app.vazovsky.mypills.databinding.ItemPillsTabBinding
import app.vazovsky.mypills.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class PillsTabViewHolder(
    parent: ViewGroup,
    private val onItemClick: (PillsTab) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pills_tab)) {
    private val binding by viewBinding(ItemPillsTabBinding::bind)

    fun bind(item: PillsTab) = with(binding.root) {
        text = item.tabName
        isChecked = item.selected
        isCloseIconVisible = isChecked
        setOnClickListener {
            isChecked = !isChecked
            onItemClick(item)
        }
    }
}
