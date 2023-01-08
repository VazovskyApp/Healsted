package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.MoodType
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import com.google.firebase.Timestamp
import java.time.OffsetDateTime
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {

    override fun getTodayPills(): List<Pill> {
        return listOf(
            Pill(
                id = "0",
                name = "Нурофен",
                type = PillType.CAPSULE,
                times = null,
                amount = 1F,
                startDate = Timestamp.now(),
            ),
            Pill(
                id = "0",
                name = "Нурофен",
                type = PillType.CAPSULE,
                times = null,
                amount = 1F,
                startDate = Timestamp.now(),
            ),
        )
    }

    override fun getTodayMood(): Mood {
        return Mood(
            value = MoodType.EMPTY,
            date = Timestamp.now(),
        )
    }
}