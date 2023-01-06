package app.vazovsky.healsted.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import app.vazovsky.healsted.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max

private const val DEFAULT_SNACK_BAR_TEXT_MAX_LINES = 3

fun Fragment.errorSnackbar(@StringRes message: Int, @Px marginBottom: Int = 0) {
    showErrorSnackbar(requireContext().getString(message), null, marginBottom) {}
}

fun Fragment.showErrorSnackbar(
    message: String,
    actionText: String? = null,
    @Px marginBottom: Int = 0,
    textMaxLines: Int = DEFAULT_SNACK_BAR_TEXT_MAX_LINES,
    action: () -> Unit = {},
) {
    showCustomSnackbar(
        if (this is BottomSheetDialogFragment) dialog?.window?.decorView else view,
        message,
        actionText,
        marginBottom,
        textMaxLines = textMaxLines,
        action = action,
    )
}

fun Activity.showSnackbar(
    view: View?,
    message: String,
    actionText: String? = null,
    @Px marginBottom: Int = 0,
    action: () -> Unit,
) {
    showCustomSnackbar(view, message, actionText, marginBottom, action = action)
}

private fun showCustomSnackbar(
    view: View?,
    message: String,
    actionText: String? = null,
    @Px marginBottom: Int = 0,
    @AttrRes backgroundTintAttrRes: Int = R.attr.errorSnackbarBackgroundColor,
    textMaxLines: Int = DEFAULT_SNACK_BAR_TEXT_MAX_LINES,
    action: () -> Unit
) {
    view?.apply {
        val backgroundTintRes = context.resolveAttribute(backgroundTintAttrRes)
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).apply {
            setBackgroundTint(context.getColorCompat(backgroundTintRes))
            setTextColor(context.getColorCompat(R.color.white))
            setActionTextColor(context.getColorCompat(R.color.white))

            val wordsCount = message.split("\\s+|\\r|\\n".toRegex()).size
            val calculatedDuration = wordsCount * 300 + 1000
            duration = max(calculatedDuration, 2000)

            if (actionText != null) {
                setAction(actionText) { action() }
            }
        }

        // форматирование текста
        val snackbarView = snackbar.view

        if (marginBottom != 0) {
            val res = snackbarView.context.resources
            val defaultMargin = res.getDimension(R.dimen.padding_16)
            snackbarView.translationY = -(defaultMargin + marginBottom)
        }

        val textViewMessage = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        with(textViewMessage) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_14))
            maxLines = textMaxLines
            updatePadding(
                left = resources.getDimensionPixelSize(R.dimen.padding_4),
                right = resources.getDimensionPixelSize(R.dimen.padding_4)
            )
            text = message
        }
        val textViewAction = snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
        textViewAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_14))

        snackbar.show()
    }
}

fun Fragment.showSoftKeyboard(view: View) {
    val inputMethodManager = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.showSoftInput(view, 0)
}

fun Fragment.hideSoftKeyboard() {
    view?.hideSoftKeyboard()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
