package app.vazovsky.healsted.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class PillsRepositoryImpl @Inject constructor() : PillsRepository {

    private val listOfTabs = listOf(
        PillsTab(
            tabName = "Все",
            slot = PillsTabSlot.ALL,
            selected = true,
        ),
        PillsTab(
            tabName = "Активные",
            slot = PillsTabSlot.ACTIVE,
            selected = false,
        ),
        PillsTab(
            tabName = "Завершенные",
            slot = PillsTabSlot.COMPLETED,
            selected = false,
        ),
    )

    private val listOfPills = listOf(
        Pill(
            id = "0",
            name = "Нурофен",
            type = PillType.CAPSULE,
            dates = null,
            amount = 1F,
            startDate = LocalDate.now(),
        ),
        Pill(
            id = "0",
            name = "Нурофен",
            type = PillType.CAPSULE,
            dates = null,
            amount = 1F,
            startDate = LocalDate.now().minusDays(2),
        ),
        Pill(
            id = "0",
            name = "Нурофен",
            type = PillType.CAPSULE,
            dates = null,
            amount = 1F,
            startDate = LocalDate.now().minusDays(2),
            endDate = LocalDate.now().plusDays(20)
        )
    )

    override fun getTabs() = listOfTabs

    override fun getPills(slot: PillsTabSlot?) = when (slot) {
        PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > LocalDate.now() }
        PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < LocalDate.now() }
        PillsTabSlot.ALL -> listOfPills
        else -> listOfPills
    }
}