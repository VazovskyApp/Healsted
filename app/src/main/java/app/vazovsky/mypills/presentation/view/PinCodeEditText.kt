package app.vazovsky.mypills.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import app.vazovsky.mypills.R
import app.vazovsky.mypills.extensions.getColorCompat
import app.vazovsky.mypills.extensions.resolveAttribute
import kotlin.math.min
import com.google.android.material.R as MaterialR


private const val DEFAULT_CODE_LENGTH = 4
private const val NUMBER_RATION_WIDTH = 28
private const val NUMBER_RATION_HEIGHT = 44
private const val HALF_BLOCK_COEF = 0.5f

class PinCodeEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val errorTextColor = context.getColorCompat(context.resolveAttribute(MaterialR.attr.colorError))
    private val defaultTextColor = context.getColorCompat(context.resolveAttribute(android.R.attr.textColor))
    private val placeholderRadius = resources.getDimension(R.dimen.pin_code_placeholder_radius)
    private val space = resources.getDimensionPixelSize(R.dimen.pin_code_space)
    private val placeholderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColorCompat(context.resolveAttribute(android.R.attr.textColorHighlight))
    }

    var isError: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    var codeLength: Int = DEFAULT_CODE_LENGTH
        set(value) {
            field = value
            filters = arrayOf(InputFilter.LengthFilter(value))
            textWidthsArray = FloatArray(value)
            invalidate()
        }

    private var numberWidth = 0
    private var numberHeight = 0
    private var textWidthsArray = FloatArray(codeLength)
    var onCodeCompleteListener: (String) -> Unit = {}

    init {
        setBackgroundResource(0)
        numberHeight = minimumHeight
        numberWidth = numberHeight * NUMBER_RATION_WIDTH / NUMBER_RATION_HEIGHT
        filters = arrayOf(InputFilter.LengthFilter(codeLength))
        doAfterTextChanged {
            val code = it?.toString().orEmpty()
            if (code.length == codeLength) {
                onCodeCompleteListener(code)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val offsetFromCenter = if (codeLength % 2 == 0) {
            numberWidth * (codeLength / 2) + (codeLength / 2 - HALF_BLOCK_COEF) * space
        } else {
            space * (codeLength / 2) + (codeLength / 2 + HALF_BLOCK_COEF) * numberWidth
        }
        var startX = width / 2f - offsetFromCenter
        val bottom = height
        val textLength = min(text?.length ?: 0, codeLength)

        paint.color = if (isError) errorTextColor else defaultTextColor
        paint.getTextWidths(text, 0, textLength, textWidthsArray)

        for (charIndex in 0 until codeLength) {
            if (charIndex < textLength) {
                // рисуем цифру
                val middle = startX + numberWidth / 2
                canvas.drawText(
                    text.toString(),
                    charIndex,
                    charIndex + 1,
                    middle - textWidthsArray[charIndex] / 2,
                    bottom / 2f + textSize / 2f,
                    paint
                )
            } else {
                // рисуем точку
                canvas.drawCircle((startX + numberWidth / 2), bottom / 2f, placeholderRadius, placeholderPaint)
            }

            startX += numberWidth + space
        }
    }
}
