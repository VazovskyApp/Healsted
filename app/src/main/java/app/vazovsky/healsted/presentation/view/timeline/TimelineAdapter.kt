package app.vazovsky.healsted.presentation.view.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.healsted.R
import java.text.DateFormatSymbols
import java.util.*

class TimelineAdapter(private val timelineView: TimelineView, private var selectedPosition: Int) :
    RecyclerView.Adapter<TimelineAdapter.ViewHolder?>() {
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var deactivatedDates: List<Date>
    private var listener: OnDateSelectedListener? = null
    private var selectedView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        resetCalendar()
        calendar.add(Calendar.DAY_OF_YEAR, position)
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val isDisabled = holder.bind(month, day, dayOfWeek, year, position)
        holder.rootView.setOnClickListener { v ->
            selectedView?.background = null
            if (!isDisabled) {
                v.background = timelineView.resources.getDrawable(R.drawable.background_shape)
                selectedPosition = position
                selectedView = v
                if (listener != null) listener!!.onDateSelected(year, month, day, dayOfWeek)
            } else {
                if (listener != null) listener!!.onDisabledDateSelected(year, month, day, dayOfWeek, isDisabled)
            }
        }
    }

    private fun resetCalendar() {
        calendar.set(
            timelineView.year, timelineView.month, timelineView.date,
            1, 0, 0
        )
    }

    /**
     * Set the position of selected date
     * @param selectedPosition active date Position
     */
    fun setSelectedPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
    }

    override fun getItemCount() = Int.MAX_VALUE

    fun disableDates(dates: List<Date>) {
        deactivatedDates = dates
        notifyDataSetChanged()
    }

    fun setDateSelectedListener(listener: OnDateSelectedListener?) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monthView: TextView
        private val dateView: TextView
        private val dayView: TextView
        val rootView: View

        init {
            monthView = itemView.findViewById(R.id.monthView)
            dateView = itemView.findViewById(R.id.dateView)
            dayView = itemView.findViewById(R.id.dayView)
            rootView = itemView.findViewById(R.id.rootView)
        }

        fun bind(month: Int, day: Int, dayOfWeek: Int, year: Int, position: Int): Boolean {
            monthView.setTextColor(timelineView.monthTextColor)
            dateView.setTextColor(timelineView.dateTextColor)
            dayView.setTextColor(timelineView.dayTextColor)
            dayView.text = WEEK_DAYS[dayOfWeek].uppercase(Locale.US)
            monthView.text = MONTH_NAME[month].uppercase(Locale.US)
            dateView.text = day.toString()
            if (selectedPosition == position) {
                rootView.background = timelineView.resources.getDrawable(R.drawable.background_shape)
                selectedView = rootView
            } else {
                rootView.setBackground(null)
            }
            for (date in deactivatedDates) {
                val tempCalendar: Calendar = Calendar.getInstance()
                tempCalendar.setTime(date)
                if (tempCalendar.get(Calendar.DAY_OF_MONTH) == day && tempCalendar.get(Calendar.MONTH) == month && tempCalendar.get(
                        Calendar.YEAR
                    ) == year
                ) {
                    monthView.setTextColor(timelineView.disabledDateColor)
                    dateView.setTextColor(timelineView.disabledDateColor)
                    dayView.setTextColor(timelineView.disabledDateColor)
                    rootView.setBackground(null)
                    return true
                }
            }
            return false
        }
    }

    companion object {
        private const val TAG = "TimelineAdapter"
        private val WEEK_DAYS: Array<String> = DateFormatSymbols.getInstance().getShortWeekdays()
        private val MONTH_NAME: Array<String> = DateFormatSymbols.getInstance().getShortMonths()
    }
}