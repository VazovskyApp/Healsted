package app.vazovsky.mypills.presentation.timeline

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Nullable
import app.vazovsky.mypills.R
import java.util.*


class DatePickerTimeline : LinearLayout {
    private var timelineView: TimelineView? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs, defStyleAttr)
    }

    fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val view: View = inflate(getContext(), R.layout.date_picker_timeline, this)
        timelineView = view.findViewById(R.id.timelineView)

        // load Default values
        val a: TypedArray = getContext().obtainStyledAttributes(
            attrs,
            R.styleable.DatePickerTimeline, defStyleAttr, 0
        )
        timelineView!!.dayTextColor =
            a.getColor(R.styleable.DatePickerTimeline_dayTextColor, resources.getColor(R.color.black))
        timelineView!!.dateTextColor =
            a.getColor(R.styleable.DatePickerTimeline_dateTextColor, resources.getColor(R.color.black))
        timelineView!!.monthTextColor =
            a.getColor(R.styleable.DatePickerTimeline_monthTextColor, resources.getColor(R.color.black))
        timelineView!!.disabledDateColor =
            a.getColor(R.styleable.DatePickerTimeline_disabledColor, resources.getColor(R.color.grey))

//        timelineView.setMonthTextSize(a.getDimension(R.styleable.DatePickerTimeline_monthTextSize, getResources().getDimension(R.dimen.monthTextSize)));
//        timelineView.setDateTextSize(a.getDimension(R.styleable.DatePickerTimeline_dateTextSize, getResources().getDimension(R.dimen.dateTextSize)));
//        timelineView.setDayTextSize(a.getDimension(R.styleable.DatePickerTimeline_dayTextSize, getResources().getDimension(R.dimen.dayTextSize)));
        timelineView?.deactivateDates(listOf())
        a.recycle()
        timelineView!!.invalidate()
    }

    /**
     * Sets the color for date text
     * @param color the color of the date text
     */
    fun setDateTextColor(color: Int) {
        timelineView!!.dateTextColor = color
    }

    /**
     * Sets the color for day text
     * @param color the color of the day text
     */
    fun setDayTextColor(color: Int) {
        timelineView!!.dayTextColor = color
    }

    /**
     * Sets the color for month
     * @param color the color of the month text
     */
    fun setMonthTextColor(color: Int) {
        timelineView!!.monthTextColor = color
    }

    /**
     * Sets the color for disabled dates
     * @param color the color of the date
     */
    fun setDisabledDateColor(color: Int) {
        timelineView!!.disabledDateColor = color
    }

    /**
     * Register a callback to be invoked when a date is selected.
     * @param listener the callback that will run
     */
    fun setOnDateSelectedListener(listener: OnDateSelectedListener?) {
        timelineView!!.setOnDateSelectedListener(listener)
    }

    /**
     * Set a Start date for the calendar (Default, 1 Jan 1970)
     * @param year start year
     * @param month start month
     * @param date start date
     */
    fun setInitialDate(year: Int, month: Int, date: Int) {
        timelineView!!.setInitialDate(year, month, date)
    }

    /**
     * Set selected background to active date
     * @param date Active Date
     */
    fun setActiveDate(date: Calendar?) {
        if (date != null) {
            timelineView!!.setActiveDate(date)
        }
    }

    /**
     * Deactivate dates from the calendar. User won't be able to select
     * the deactivated date.
     * @param dates Array of Dates
     */
    fun deactivateDates(dates: List<Date>) {
        timelineView!!.deactivateDates(dates)
    }
}