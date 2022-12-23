package app.vazovsky.mypills.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.style.URLSpan
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) {
    TextViewCompat.setTextAppearance(this, resId)
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun TextView.setTextFromHtml(text: String) {
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(text)
    }
}

/**
 * При использовании autoLink удаляется автоматическое подчёркивание
 */
fun TextView.stripUnderlines() {
    if (text !is Spannable) return

    val spannableText = text as Spannable
    val spans = spannableText.getSpans(0, spannableText.length, URLSpan::class.java)
    for (span in spans) {
        val start = spannableText.getSpanStart(span)
        val end = spannableText.getSpanEnd(span)
        spannableText.removeSpan(span)

        val newSpan = object : URLSpan(span.url) {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        spannableText.setSpan(newSpan, start, end, 0)
    }
}