package app.vazovsky.healsted.managers

import android.content.Context
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.extensions.formatDecimalWithSpacing
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataTypeFormatter @Inject constructor(@ApplicationContext val context: Context) {
    fun formatPill(pill: Pill) = buildString {
        val amount = pill.amount.toInt()
        val amountDecimal = pill.amount.formatDecimalWithSpacing()
        append(
            when (pill.type) {
                PillType.TABLETS -> context.resources.getQuantityString(R.plurals.tablets, amount, amountDecimal)
                PillType.CAPSULE -> context.resources.getQuantityString(R.plurals.capsules, amount, amountDecimal)
                PillType.INJECTION -> context.resources.getQuantityString(R.plurals.injections, amount, amountDecimal)
                PillType.PROCEDURES -> context.resources.getQuantityString(R.plurals.procedures, amount, amountDecimal)
                PillType.DROPS -> context.resources.getQuantityString(R.plurals.drops, amount, amountDecimal)
                PillType.SPRAY -> context.resources.getQuantityString(R.plurals.spray, amount, amountDecimal)
                PillType.CREAM -> amountDecimal + " " + context.resources.getString(R.string.pill_type_cream)
                PillType.LIQUID -> amountDecimal + " " + context.resources.getString(R.string.pill_type_liquid)
            }
        )
    }

    fun formatPill(amount: Float, type: PillType?) = buildString {
        val amountInt = amount.toInt()
        val amountDecimal = amount.formatDecimalWithSpacing()
        append(
            when (type) {
                PillType.TABLETS -> context.resources.getQuantityString(R.plurals.tablets, amountInt, amountDecimal)
                PillType.CAPSULE -> context.resources.getQuantityString(R.plurals.capsules, amountInt, amountDecimal)
                PillType.INJECTION -> context.resources.getQuantityString(R.plurals.injections, amountInt, amountDecimal)
                PillType.PROCEDURES -> context.resources.getQuantityString(R.plurals.procedures, amountInt, amountDecimal)
                PillType.DROPS -> context.resources.getQuantityString(R.plurals.drops, amountInt, amountDecimal)
                PillType.SPRAY -> context.resources.getQuantityString(R.plurals.spray, amountInt, amountDecimal)
                PillType.CREAM -> amountDecimal + " " + context.resources.getString(R.string.pill_type_cream)
                PillType.LIQUID -> amountDecimal + " " + context.resources.getString(R.string.pill_type_liquid)
                else -> amountDecimal + " " + context.resources.getString(R.string.pill_type_other)
            }
        )
    }
}