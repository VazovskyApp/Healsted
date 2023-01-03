package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView

class TextViewNoClipping(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private class NonClippableCanvas(@NonNull val bitmap: Bitmap) : Canvas(bitmap) {
        override fun clipRect(left: Float, top: Float, right: Float, bottom: Float): Boolean {
            return true
        }
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
                // If for some reasons the bitmap cannot be created,
                // we fall back on default rendering (potentially cropping the text).
                nonClippableCanvas?.bitmap?.recycle()
                nonClippableCanvas = null
            }
        }

        super.onSizeChanged(width, height, oldwidth, oldheight)
    }

    override fun onDraw(canvas: Canvas) {
        nonClippableCanvas?.let {
            // Clear the canvas from the previous font.
            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

            // Draw on the canvas (-> bitmap) that will use clipping on the NonClippableCanvas, resulting in no-clipping
            super.onDraw(it)

            // Finally draw the bitmap that contains the rendered text (no clipping used here, will display on top of padding)
            canvas.drawBitmap(it.bitmap, 0f, 0f, null)
        } ?: super.onDraw(canvas) // If nonClippableCanvas is not available, use default rendering process
    }
}