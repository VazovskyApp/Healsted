package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/** TextView без обрезания границ */
class TextViewNoClipping @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatTextView(context, attrs) {

    private class NonClippableCanvas(val bitmap: Bitmap) : Canvas(bitmap) {
        override fun clipRect(left: Float, top: Float, right: Float, bottom: Float) = true
    }

    private var nonClippableCanvas: NonClippableCanvas? = null

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        if ((width != oldwidth || height != oldheight) && width > 0 && height > 0) {
            nonClippableCanvas?.bitmap?.recycle()
            try {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)?.let {
                    nonClippableCanvas = NonClippableCanvas(it)
                }
            } catch (ex: Throwable) {
                nonClippableCanvas?.bitmap?.recycle()
                nonClippableCanvas = null
            }
        }

        super.onSizeChanged(width, height, oldwidth, oldheight)
    }

    override fun onDraw(canvas: Canvas) {
        nonClippableCanvas?.let {
            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            super.onDraw(it)
            canvas.drawBitmap(it.bitmap, 0f, 0f, null)
        } ?: super.onDraw(canvas)
    }
}