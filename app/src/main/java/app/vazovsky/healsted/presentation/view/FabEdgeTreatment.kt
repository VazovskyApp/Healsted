package app.vazovsky.healsted.presentation.view

import androidx.annotation.Px
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/** Добавление выреза под FAB на сторону */
class FabEdgeTreatment(
    @Px private val fabDiameter: Float,
    @Px private val fabMargin: Float,
    @Px private val centerCornersRadius: Float
) : EdgeTreatment() {

    private val radius = fabDiameter / 2.0f + fabMargin

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
        shapePath.lineTo(
            /* x = */ center - radius - centerCornersRadius,
            /* y = */ 0f,
        )
        shapePath.addArc(
            /* left = */ center - radius - centerCornersRadius * 2,
            /* top = */ 0f,
            /* right = */ center - radius,
            /* bottom = */ centerCornersRadius * 2,
            /* startAngle = */ 270f,
            /* sweepAngle = */ 90f,
        )
        shapePath.addArc(
            /* left = */ center - radius,
            /* top = */ centerCornersRadius - radius,
            /* right = */ center + radius,
            /* bottom = */ centerCornersRadius + radius,
            /* startAngle = */ 180f,
            /* sweepAngle = */ -180f,
        )
        shapePath.addArc(
            /* left = */ center + radius,
            /* top = */ 0f,
            /* right = */ center + radius + centerCornersRadius * 2,
            /* bottom = */ centerCornersRadius * 2,
            /* startAngle = */ 180f,
            /* sweepAngle = */ 90f,
        )
        shapePath.lineTo(
            /* x = */ length,
            /* y = */ 0f,
        )
    }
}