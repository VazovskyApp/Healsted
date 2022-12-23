package app.vazovsky.mypills.extensions

import android.content.Context
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {

    val initialPadding = recordInitialPaddingForView(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }

    requestApplyInsetsWhenAttached()
}

/**
 * Метод выставляет у вью паддинг, равный высоте статус бара (верхнему системному инсету).
 * При этом помечает, что обработал top inset
 * */
fun View.fitTopInsetsWithPadding() {
    this.doOnApplyWindowInsets { view, insets, paddings ->
        val windowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(
            top = windowInsets.top + paddings.top
        )
        WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(), Insets.of(
                windowInsets.left, 0, windowInsets.right, windowInsets.bottom
            )
        ).build()
    }
}

/**
 * Метод выставляет у вью паддинг сверху, равный высоте статус бара (верхнему системному инсету),
 * и паддинг снизу, равный высоте клавиатуры.
 * При этом помечает, что обработал top и bottom insets
 */
fun View.fitKeyboardInsetsWithPadding() {
    this.doOnApplyWindowInsets { view, insets, paddings ->
        val windowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        view.updatePadding(
            top = windowInsets.top + paddings.top, bottom = windowInsets.bottom + paddings.bottom
        )
        WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(), Insets.of(
                windowInsets.left, 0, windowInsets.right, 0
            )
        ).build()
    }
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

/** Обновление внешних отступов */
fun View.updateMargins(
    left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null
) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    lp.setMargins(
        left ?: lp.leftMargin, top ?: lp.topMargin, right ?: lp.rightMargin, bottom ?: lp.bottomMargin
    )

    layoutParams = lp
}

/** Скрытие системной клавиатуры */
fun View.hideSoftKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

/** Увеличение области нажатия */
fun View.increaseTouchArea(@DimenRes areaRes: Int) {
    val parent: View = parent as View
    val areaDimension = parent.context.resources.getDimensionPixelSize(areaRes)
    post {
        val rect = Rect()
        getHitRect(rect)
        with(rect) {
            top -= areaDimension
            bottom += areaDimension
            left -= areaDimension
            right += areaDimension
        }
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}