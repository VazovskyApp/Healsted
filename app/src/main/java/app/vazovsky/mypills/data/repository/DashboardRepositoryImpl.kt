package app.vazovsky.mypills.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.mypills.data.model.Mood
import app.vazovsky.mypills.data.model.MoodType
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillType
import java.time.OffsetDateTime
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {
    override fun getTodayPills(): List<Pill> {
        //return emptyList()
        return listOf(
            Pill(
                id = "0",
                name = "Нурафен",
                type = PillType.CAPSULE,
                dates = null,
                amount = 1F,
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodayMood(): Mood {
        return Mood(
            id = "0",
            value = MoodType.EMPTY,
            date = OffsetDateTime.now(),
        )
    }
}