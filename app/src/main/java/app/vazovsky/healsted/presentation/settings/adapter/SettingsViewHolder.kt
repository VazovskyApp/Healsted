package app.vazovsky.healsted.presentation.settings.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.SettingsItem
import app.vazovsky.healsted.databinding.ItemSettingsBinding
import app.vazovsky.healsted.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class SettingsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (SettingsItem) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_settings)) {

    private val binding by viewBinding(ItemSettingsBinding::bind)

    fun bind(item: SettingsItem) = with(binding) {
        root.setOnClickListener { onItemClick.invoke(item) }

        textViewTitle.text = item.type.toString()
        item.icon?.let {
            imageViewIcon.setBackgroundResource(it)
            imageViewIcon.background.setTint(root.context.getColor(R.color.white))
        }
    }
}