package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot
import java.time.OffsetDateTime
import javax.inject.Inject

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
            times = null,
            amount = 1F,
            startDate = OffsetDateTime.now(),
        ),
        Pill(
            id = "0",
            name = "Нурофен",
            type = PillType.CAPSULE,
            times = null,
            amount = 1F,
            startDate = OffsetDateTime.now().minusDays(2),
        ),
        Pill(
            id = "0",
            name = "Нурофен",
            type = PillType.CAPSULE,
            times = null,
            amount = 1F,
            startDate = OffsetDateTime.now().minusDays(2),
            endDate = OffsetDateTime.now().plusDays(20)
        )
    )

    override fun getTabs() = listOfTabs

    override fun getPills(slot: PillsTabSlot?) = when (slot) {
        PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > OffsetDateTime.now() }
        PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < OffsetDateTime.now() }
        PillsTabSlot.ALL -> listOfPills
        else -> listOfPills
    }
}