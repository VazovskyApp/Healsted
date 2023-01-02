package app.vazovsky.mypills.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.mypills.data.model.Mood
import app.vazovsky.mypills.data.model.MoodType
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillType
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {

    // TODO поправить
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodayPills(): List<Pill> {
        //return emptyList()
        return listOf(
            Pill(
                id = "0",
                name = "Нурофен",
                type = PillType.CAPSULE,
                dates = null,
                amount = 1F,
                startDate = LocalDate.now(),
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