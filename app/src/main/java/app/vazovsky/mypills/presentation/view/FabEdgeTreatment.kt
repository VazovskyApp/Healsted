package app.vazovsky.mypills.presentation.view

import androidx.annotation.Px
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * Добавляет вырез под FAB на сторону
 */
class FabEdgeTreatment(
    @Px private val fabDiameter: Float,
    @Px private val fabMargin: Float,
    @Px private val centerCornersRadius: Float
) : EdgeTreatment() {

    private val radius = fabDiameter / 2.0f + fabMargin

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {

        // до первой арки вниз
        shapePath.lineTo(center - radius - centerCornersRadius, 0f)

        // первая маленькая арка вниз
        shapePath.addArc(
            center - radius - centerCornersRadius * 2,
            0f,
            center - radius,
            centerCornersRadius * 2,
            270f,
            90f
        )

        // большой полугруг вниз
        shapePath.addArc(
            center - radius,
            centerCornersRadius - radius,
            center + radius,
            centerCornersRadius + radius,
            180f,
            -180f,
        )

        // маленькая арка вверх
        shapePath.addArc(
            center + radius,
            0f,
            center + radius + centerCornersRadius * 2,
            centerCornersRadius * 2,
            180f,
            90f,
        )

        // замыкающая линия
        shapePath.lineTo(length, 0f)
    }
}