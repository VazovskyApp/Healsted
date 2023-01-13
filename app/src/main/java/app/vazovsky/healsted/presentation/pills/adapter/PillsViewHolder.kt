package app.vazovsky.healsted.presentation.pills.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.databinding.ItemPillBinding
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.getColorIdFromPosition
import app.vazovsky.healsted.extensions.inflate
import app.vazovsky.healsted.managers.DateFormatter
import by.kirich1409.viewbindingdelegate.viewBinding
import java.util.*

class PillsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Pill) -> Unit,
    private val dateFormatter: DateFormatter,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_pill)) {
    private val binding by viewBinding(ItemPillBinding::bind)

    fun bind(item: Pill, position: Int) = with(binding) {
        root.apply {
            setOnClickListener { onItemClick(item) }
            setCardBackgroundColor(context.getColor(position.getColorIdFromPosition()))
        }

        textViewName.text = item.name.capitalizeFirstChar(Locale.getDefault())
        textViewDates.text = dateFormatter.formatPeriod(item.startDate, item.endDate)
        imageViewIcon.apply {
            setBackgroundResource(
                when (item.type) {
                    PillType.TABLETS -> R.drawable.ic_pill_tablets
                    PillType.CAPSULE -> R.drawable.ic_pill_capsule
                    PillType.INJECTION -> R.drawable.ic_pill_injection
                    PillType.PROCEDURES -> R.drawable.ic_pill_procedures
                    PillType.DROPS -> R.drawable.ic_pill_drops
                    PillType.LIQUID -> R.drawable.ic_pill_liquid
                    PillType.CREAM -> R.drawable.ic_pill_cream
                    PillType.SPRAY -> R.drawable.ic_pill_spray
                }
            )
        }
    }
}