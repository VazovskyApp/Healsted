package app.vazovsky.healsted.presentation.view.rating

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.MoodType

class EmojiRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private class Smiley(var image: ImageView, var text: TextView, var status: MoodType)

    private var rating: MoodType = MoodType.EMPTY
    private var ratingChangeListener: OnRateChangeListener? = null

    private var _view: View
    private lateinit var btnAwful: LinearLayout
    private lateinit var btnBad: LinearLayout
    private lateinit var btnOkay: LinearLayout
    private lateinit var btnGood: LinearLayout
    private lateinit var btnGreat: LinearLayout

    private lateinit var ivAwful: ImageView
    private lateinit var ivBad: ImageView
    private lateinit var ivOkay: ImageView
    private lateinit var ivGood: ImageView
    private lateinit var ivGreat: ImageView

    private lateinit var tvAwful: TextView
    private lateinit var tvBad: TextView
    private lateinit var tvOkay: TextView
    private lateinit var tvGood: TextView
    private lateinit var tvGreat: TextView

    private var showText: Boolean = true
    private var showAllText: Boolean = false
    private var color: Int = 0
    private var fontFamilyId = 0
    private var readOnly = false

    private lateinit var smileyList: List<Smiley>

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _view = inflater.inflate(R.layout.item_rate_view, this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EmojiRatingBar,
            0, 0
        ).apply {

            try {
                showText = getBoolean(R.styleable.EmojiRatingBar_showText, true)
                color = getColor(R.styleable.EmojiRatingBar_titleColor, 0)
                showAllText = getBoolean(R.styleable.EmojiRatingBar_showAllText, false)
                rating = MoodType.values()[getInt(R.styleable.EmojiRatingBar_defaultValue, 0)]
                fontFamilyId = getResourceId(R.styleable.EmojiRatingBar_android_fontFamily, 0)
                readOnly = false
            } finally {
                recycle()
            }
        }
        initType()
    }

    private fun initType() {
        binding()
        handleRatingClick()
        if (color > 0) {
            setInitialColor(color)
        }
        if (fontFamilyId > 0) {
            setTypeFace(fontFamilyId)
        }
        setCurrentRateStatus(rating)
        if (showAllText) {
            hideAllTitles(false)
        } else if (!showText) {
            hideAllTitles(true)
        }
        setReadOnly(false)
    }


    private fun binding() {
        btnAwful = _view.findViewById(R.id.btn_awful)
        btnBad = _view.findViewById(R.id.btn_bad)
        btnOkay = _view.findViewById(R.id.btn_okay)
        btnGood = _view.findViewById(R.id.btn_good)
        btnGreat = _view.findViewById(R.id.btn_great)

        ivAwful = _view.findViewById(R.id.iv_awful)
        ivBad = _view.findViewById(R.id.iv_bad)
        ivOkay = _view.findViewById(R.id.iv_okay)
        ivGood = _view.findViewById(R.id.iv_good)
        ivGreat = _view.findViewById(R.id.iv_great)

        tvAwful = findViewById(R.id.tv_awful)
        tvBad = findViewById(R.id.tv_bad)
        tvOkay = findViewById(R.id.tv_okay)
        tvGood = findViewById(R.id.tv_good)
        tvGreat = findViewById(R.id.tv_great)

        smileyList = listOf(
            Smiley(ivAwful, tvAwful, MoodType.AWFUL),
            Smiley(ivBad, tvBad, MoodType.BAD),
            Smiley(ivOkay, tvOkay, MoodType.OKAY),
            Smiley(ivGood, tvGood, MoodType.GOOD),
            Smiley(ivGreat, tvGreat, MoodType.GREAT)
        )
    }

    private fun handleRatingClick() {
        btnAwful.setOnClickListener {
            scaleEmoji(ivAwful, btnAwful)
            setCurrentRateStatus(MoodType.AWFUL)
        }

        btnBad.setOnClickListener {
            scaleEmoji(ivBad, btnBad)
            setCurrentRateStatus(MoodType.BAD)
        }

        btnOkay.setOnClickListener {
            scaleEmoji(ivOkay, btnOkay)
            setCurrentRateStatus(MoodType.OKAY)
        }

        btnGood.setOnClickListener {
            scaleEmoji(ivGood, btnGood)
            setCurrentRateStatus(MoodType.GOOD)
        }

        btnGreat.setOnClickListener {
            scaleEmoji(ivGreat, btnGreat)
            setCurrentRateStatus(MoodType.GREAT)
        }
    }

    fun setRateChangeListener(listener: OnRateChangeListener) {
        ratingChangeListener = listener
    }

    fun getCurrentRateStatus(): MoodType {
        return rating
    }

    fun setShowText(showText: Boolean) {
        this.showText = showText
        for (smiley in smileyList) {
            if (smiley.status == rating) smiley.text.visibility = if (showText) View.VISIBLE else View.INVISIBLE
            else smiley.text.visibility = View.INVISIBLE
        }
    }

    fun setShowAllText(showAllText: Boolean) {
        this.showAllText = showAllText
        val visible = if (showAllText) View.VISIBLE else View.INVISIBLE
        for (smiley in smileyList) {
            smiley.text.visibility = visible
        }
    }

    fun getShowText(): Boolean {
        return showText
    }

    fun getShowAllText(): Boolean {
        return showAllText
    }

    fun setReadOnly(readOnly: Boolean) {
        this.readOnly = readOnly
    }

    fun setCurrentRateStatus(rateStatus: MoodType) {
        if (this.readOnly) return
        rating = rateStatus
        ratingChangeListener?.onRateChanged(rating)

        for (smiley in smileyList) {
            smiley.text.visibility = View.INVISIBLE
            if (smiley.status == rateStatus) {
                getRatingImageResource(smiley.status)?.let {
                    smiley.image.setImageResource(it)
                    smiley.image.colorFilter = null
                }
                if (showText) smiley.text.visibility = View.VISIBLE
            } else {
                getRatingImageResource(smiley.status)?.let {
                    smiley.image.setImageResource(it)
                    smiley.image.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
                }
            }

            if (showAllText) {
                smiley.text.visibility = View.VISIBLE
            }
        }
    }


    private fun getRatingImageResource(rateStatus: MoodType) =
        when (rateStatus) {
            MoodType.AWFUL -> R.drawable.ic_rating_awful
            MoodType.BAD -> R.drawable.ic_rating_bad
            MoodType.OKAY -> R.drawable.ic_rating_okay
            MoodType.GOOD -> R.drawable.ic_rating_good
            MoodType.GREAT -> R.drawable.ic_rating_great
            MoodType.EMPTY -> null
        }

    fun setTypeFace(@FontRes font: Int) {
        tvAwful.typeface = ResourcesCompat.getFont(context, font)
        tvBad.typeface = ResourcesCompat.getFont(context, font)
        tvOkay.typeface = ResourcesCompat.getFont(context, font)
        tvGood.typeface = ResourcesCompat.getFont(context, font)
        tvGreat.typeface = ResourcesCompat.getFont(context, font)
    }

    fun setTypeFaceFromAssets(fontPath: String) {
        tvAwful.typeface = Typeface.createFromAsset(context.assets, fontPath)
        tvBad.typeface = Typeface.createFromAsset(context.assets, fontPath)
        tvOkay.typeface = Typeface.createFromAsset(context.assets, fontPath)
        tvGood.typeface = Typeface.createFromAsset(context.assets, fontPath)
        tvGreat.typeface = Typeface.createFromAsset(context.assets, fontPath)
    }

    fun setTitleColor(color: Int) {
        tvAwful.setTextColor(ContextCompat.getColor(context, color))
        tvBad.setTextColor(ContextCompat.getColor(context, color))
        tvOkay.setTextColor(ContextCompat.getColor(context, color))
        tvGood.setTextColor(ContextCompat.getColor(context, color))
        tvGreat.setTextColor(ContextCompat.getColor(context, color))
    }

    private fun setInitialColor(color: Int) {
        tvAwful.setTextColor(color)
        tvBad.setTextColor(color)
        tvOkay.setTextColor(color)
        tvGood.setTextColor(color)
        tvGreat.setTextColor(color)
    }

    private fun hideAllTitles(hide: Boolean = true) {
        val titleVisibility = if (hide) View.GONE else View.VISIBLE
        tvAwful.visibility = titleVisibility
        tvBad.visibility = titleVisibility
        tvOkay.visibility = titleVisibility
        tvGood.visibility = titleVisibility
        tvGreat.visibility = titleVisibility
    }

    fun setAwfulEmojiTitle(title: String) {
        tvAwful.text = title
    }

    fun setBadEmojiTitle(title: String) {
        tvBad.text = title
    }

    fun setOkayEmojiTitle(title: String) {
        tvOkay.text = title
    }

    fun setGoodEmojiTitle(title: String) {
        tvGood.text = title
    }

    fun setGreatEmojiTitle(title: String) {
        tvGreat.text = title
    }

    private fun scaleEmoji(targetView: View, disableView: View) {
        if (this.readOnly) return
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(targetView, scaleX, scaleY)
        animator.repeatCount = 1
        animator.duration = 200
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(disableView)
        animator.start()
    }

    private fun ValueAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                view.isEnabled = true
            }
        })
    }

    interface OnRateChangeListener {
        fun onRateChanged(rateStatus: MoodType)
    }
}