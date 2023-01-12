package app.vazovsky.healsted.presentation.pilleditor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.databinding.ItemPillTypeBinding
import app.vazovsky.healsted.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber

class PillTypeViewHolder(
    parent: ViewGroup,
    private val onItemClick: (PillTypeItem) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill_type)) {
    private val binding by viewBinding(ItemPillTypeBinding::bind)

    fun bind(item: PillTypeItem) = with(binding) {
        root.setOnClickListener {
            Timber.d("CLICK HOLDER")
            onItemClick(item)
        }
        root.setBackgroundColor(root.context.getColor(if (item.selected) R.color.pillsCardTransparent50 else R.color.transparent))

        textViewTitle.text = item.pillType.toString()
        item.icon?.let { imageViewIcon.setBackgroundResource(it) }
    }
}