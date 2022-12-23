package app.vazovsky.mypills.extensions

import android.app.Activity
import androidx.core.graphics.Insets

/**
 * @param insets берутся из метода View.doOnApplyWindowInsets методом getInsets() WindowInsetsCompat
 * @return Показана ли клавиатура
 */
fun Activity.isKeyboardVisible(insets: Insets): Boolean {
    val keyboardRatio = 0.25
    val systemScreenHeight = this.window.decorView.height
    val heightDiff = insets.bottom + insets.top
    return heightDiff > keyboardRatio * systemScreenHeight
}
