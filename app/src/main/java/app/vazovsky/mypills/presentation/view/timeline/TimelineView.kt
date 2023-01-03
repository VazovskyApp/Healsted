package app.vazovsky.mypills.presentation.view.timeline

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimelineView : RecyclerView {
    private var adapter: TimelineAdapter? = null
    var monthTextColor = 0
    var dateTextColor = 0
    var dayTextColor = 0
    var selectedColor = 0
    var disabledDateColor = 0

    //    private float monthTextSize, dateTextSize, dayTextSize;
    var year = 0
        private set
    var month = 0
        private set
    var date = 0
        private set

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun init() {
        year = 1970
        month = 0
        date = 1
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter = TimelineAdapter(this, -1)
        setAdapter(adapter)
    }

    fun setOnDateSelectedListener(listener: OnDateSelectedListener?) {
        adapter?.setDateSelectedListener(listener)
    }

    fun setInitialDate(year: Int, month: Int, date: Int) {
        this.year = year
        this.month = month
        this.date = date
        invalidate()
    }

    /**
     * Calculates the date position and set the selected background on that date
     * @param activeDate active Date
     */
    @SuppressLint("SimpleDateFormat")
    fun setActiveDate(activeDate: Calendar) {
        try {
            val initialDate: Date = SimpleDateFormat("yyyy-MM-dd")
                .parse(year.toString() + "-" + (month + 1) + "-" + date) as Date
            val diff: Long = activeDate.time.time - initialDate.time
            val position = (diff / (1000 * 60 * 60 * 24)).toInt()
            adapter?.setSelectedPosition(position)
            invalidate()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun deactivateDates(deactivatedDates: List<Date>) {
        adapter?.disableDates(deactivatedDates)
    }
}