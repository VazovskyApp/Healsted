package app.vazovsky.mypills.extensions

import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import app.vazovsky.mypills.R
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getText(): String = editText?.text?.toString().orEmpty()

fun TextInputLayout.showError(@StringRes stringRes: Int) = showError(context.getString(stringRes))

fun TextInputLayout.showError(errorText: String) {
    if (error != errorText) {
        error = errorText
    }
    boxStrokeWidth = resources.getDimensionPixelOffset(R.dimen.text_input_stroke_width)
}

fun TextInputLayout.hideError() {
    error = null
    boxStrokeWidth = 0
}

fun TextInputLayout.isError(): Boolean {
    return error != null
}

/**
 * Ошибка ввода скрывается при вводе текста
 */
fun TextInputLayout.errorCleaner() {
    editText?.addTextChangedListener {
        if (isError()) {
            hideError()
        }
    }
}
