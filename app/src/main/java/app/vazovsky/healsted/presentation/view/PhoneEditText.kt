package app.vazovsky.healsted.presentation.view

import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.Phone
import app.vazovsky.healsted.data.model.base.biMapOf
import app.vazovsky.healsted.extensions.filterNotNumeric
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * EditText для форматирования ввода номера телефона без кода страны
 */
class PhoneEditText : androidx.appcompat.widget.AppCompatEditText {

    /** Список форматов номеров для разных кодов стран */
    private val patterns = biMapOf(
        7 to "___ ___ __ __"
    )

    private var countryCode: Int = Phone.DEFAULT_COUNTRY_CODE

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            android.R.id.paste -> {
                val primaryClip = clipboardManager.primaryClip
                if ((primaryClip?.itemCount ?: 0) > 0) {
                    val text = primaryClip?.getItemAt(0)?.text ?: ""
                    onTextPaste(text.toString())
                }
            }
        }
        return consumed
    }

    private fun onTextPaste(text: String) {
        var formattedInput = text.filterNotNumeric()
        val mask = Phone.DEFAULT_COUNTRY_CODE.toString()
        if (formattedInput.length == PHONE_LENGTH && formattedInput.startsWith(mask)) {
            formattedInput = formattedInput.substring(mask.length)
        }

        setText(formattedInput)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PhoneEditText,
            0,
            0
        )
        try {
            countryCode = a.getInt(R.styleable.PhoneEditText_countryCode, Phone.DEFAULT_COUNTRY_CODE)
        } finally {
            a.recycle()
        }
        setMask()
    }

    private fun setMask() {
        patterns[countryCode]?.let { mask ->
            val slots = UnderscoreDigitSlotsParser().parseSlots(mask)
            val inputMask: MaskImpl = MaskImpl.createTerminated(slots)
            val watcher = MaskFormatWatcher(inputMask)
            watcher.installOn(this)
        }
    }

    companion object {
        private const val PHONE_LENGTH = 11
    }
}
