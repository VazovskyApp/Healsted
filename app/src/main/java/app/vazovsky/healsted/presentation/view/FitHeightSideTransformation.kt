package app.vazovsky.healsted.presentation.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Вписывание картинки по высоте и прибивает к правому краю
 * Все что слева - обрезается
 */
class FitHeightSideTransformation(private val align: Align = Align.FIT_END) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return transform(align, toTransform, outWidth, outHeight)
    }

    enum class Align {
        FIT_END,
        FIT_START,
    }

    companion object {
        private const val ID = "app.vazovsky.healsted.presentation.view.FitHeightEndTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charset.forName("UTF-8"))

        fun transform(align: Align, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            val scaledBitmap = if (toTransform.height != outHeight) {
                val scale = toTransform.height.toFloat() / outHeight
                Bitmap.createScaledBitmap(toTransform, (toTransform.width / scale).toInt(), outHeight, true)
            } else {
                toTransform
            }
            val leftOffset = scaledBitmap.width - outWidth
            return if (align == Align.FIT_END) {
                Bitmap.createBitmap(scaledBitmap, leftOffset, 0, outWidth, outHeight)
            } else {
                val bitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawBitmap(scaledBitmap, 0f, 0f, Paint())
                bitmap
            }
        }
    }
}