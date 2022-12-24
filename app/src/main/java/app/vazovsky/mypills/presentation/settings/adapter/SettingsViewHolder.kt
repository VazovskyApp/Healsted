package app.vazovsky.mypills.presentation.settings.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.SettingsItem
import app.vazovsky.mypills.databinding.ItemSettingsBinding
import app.vazovsky.mypills.extensions.inflate
import by.kirich1409.viewbindingdelegate.viewBinding

class SettingsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (SettingsItem) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_settings)) {

    private val binding by viewBinding(ItemSettingsBinding::bind)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(item: SettingsItem) = with(binding) {
        textViewTitle.text = item.title
        item.icon?.let { imageViewIcon.background = root.context.getDrawable(it) }
        root.setOnClickListener { onItemClick.invoke(item) }
    }
}