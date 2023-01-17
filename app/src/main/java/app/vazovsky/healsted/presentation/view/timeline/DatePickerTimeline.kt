package app.vazovsky.healsted.presentation.view.timeline

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import app.vazovsky.healsted.R
import app.vazovsky.healsted.databinding.DatePickerTimelineBinding
import app.vazovsky.healsted.extensions.getColorFromAttribute
import java.util.*
import com.google.android.material.R as MaterialR

class DatePickerTimeline @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {
    private val binding = DatePickerTimelineBinding.inflate(LayoutInflater.from(context), this)

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.DatePickerTimeline, defStyle, 0)

        setColors(a)

        binding.timelineView.deactivateDates(listOf())
        a.recycle()
        binding.timelineView.invalidate()
    }

    private fun setColors(a: TypedArray) = with(binding.timelineView) {
        dayTextColor = a.getColor(
            R.styleable.DatePickerTimeline_dayTextColor,
            context.getColorFromAttribute(MaterialR.attr.colorOnBackground)
        )
        dateTextColor = a.getColor(
            R.styleable.DatePickerTimeline_dateTextColor,
            context.getColorFromAttribute(MaterialR.attr.colorOnBackground)
        )
        monthTextColor = a.getColor(
            R.styleable.DatePickerTimeline_monthTextColor,
            context.getColorFromAttribute(MaterialR.attr.colorOnBackground)
        )
        disabledDateColor = a.getColor(
            R.styleable.DatePickerTimeline_disabledColor,
            context.getColorFromAttribute(MaterialR.attr.colorOnBackground)
        )
    }

    /**
     * Sets the color for date text
     * @param color the color of the date text
     */
    fun setDateTextColor(color: Int) = with(binding) {
        timelineView.dateTextColor = color
    }

    /**
     * Sets the color for day text
     * @param color the color of the day text
     */
    fun setDayTextColor(color: Int) = with(binding) {
        timelineView.dayTextColor = color
    }

    /**
     * Sets the color for month
     * @param color the color of the month text
     */
    fun setMonthTextColor(color: Int) = with(binding) {
        timelineView.monthTextColor = color
    }

    /**
     * Sets the color for disabled dates
     * @param color the color of the date
     */
    fun setDisabledDateColor(color: Int) = with(binding) {
        timelineView.disabledDateColor = color
    }

    /**
     * Register a callback to be invoked when a date is selected.
     * @param listener the callback that will run
     */
    fun setOnDateSelectedListener(listener: OnDateSelectedListener?) = with(binding) {
        timelineView.setOnDateSelectedListener(listener)
    }

    /**
     * Set a Start date for the calendar (Default, 1 Jan 1970)
     * @param year start year
     * @param month start month
     * @param date start date
     */
    fun setInitialDate(year: Int, month: Int, date: Int) = with(binding) {
        timelineView.setInitialDate(year, month, date)
    }

    /**
     * Set selected background to active date
     * @param date Active Date
     */
    fun setActiveDate(date: Calendar?) = with(binding) {
        if (date != null) {
            timelineView.setActiveDate(date)
        }
    }

    /** Проскролит так, чтоб было видно предыдущие две даты и следующие три */
    fun scrollToActivePosition() = with(binding) {
        val activePosition = timelineView.getActivePosition()
        timelineView.scrollToPosition(if (activePosition - 2 < 0) activePosition else activePosition - 2)
    }

    fun getActiveDate() = with(binding) {
        timelineView.date
    }

    /**
     * Deactivate dates from the calendar. User won't be able to select
     * the deactivated date.
     * @param dates Array of Dates
     */
    fun deactivateDates(dates: List<Date>) = with(binding) {
        timelineView.deactivateDates(dates)
    }
}