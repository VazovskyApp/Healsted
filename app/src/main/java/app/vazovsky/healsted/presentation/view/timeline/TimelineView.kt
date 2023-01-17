package app.vazovsky.healsted.presentation.view.timeline

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.extensions.orDefault
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimelineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {
    private var adapter: TimelineAdapter? = null
    var monthTextColor = 0
    var dateTextColor = 0
    var dayTextColor = 0
    var selectedColor = 0
    var disabledDateColor = 0

    var selectedDate: Calendar? = null

    var year = 0
        private set
    var month = 0
        private set
    var date = 0
        private set

    init {
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
        return try {
            selectedDate = activeDate
            val initialDate: Date = SimpleDateFormat("yyyy-MM-dd")
                .parse(year.toString() + "-" + (month + 1) + "-" + date) as Date
            val diff: Long = activeDate.time.time - initialDate.time
            val position = (diff / (1000 * 60 * 60 * 24)).toInt()
            adapter?.setSelectedPosition(position)
            invalidate()
        } catch (e: ParseException) {
            e.printStackTrace()
            throw Exception()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getActivePosition(): Int {
        return selectedDate?.let {
            val initialDate: Date = SimpleDateFormat("yyyy-MM-dd")
                .parse(year.toString() + "-" + (month + 1) + "-" + date) as Date
            val diff: Long = it.time.time - initialDate.time
            (diff / (1000 * 60 * 60 * 24)).toInt()
        } ?: 0
    }

    fun getItemsCount() = adapter?.itemCount.orDefault()

    fun deactivateDates(deactivatedDates: List<Date>) {
        adapter?.disableDates(deactivatedDates)
    }
}