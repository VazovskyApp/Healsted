package app.vazovsky.mypills.extensions

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.resolveAttribute(resId: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(resId, outValue, true)
    return outValue.resourceId
}

fun Context.getColorFromAttribute(@AttrRes attrId: Int): Int {
    return getColorCompat(resolveAttribute(attrId))
}

fun Context.createPaintFromTextStyle(@StyleRes resId: Int): Paint {
    AppCompatTextView(this).run {
        setTextAppearanceCompat(resId)
        return paint
    }
}