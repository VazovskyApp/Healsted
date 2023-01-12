package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot
import javax.inject.Inject

class PillsRepositoryImpl @Inject constructor() : PillsRepository {

    //<editor-fold desc="Tabs">
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

    override fun getTabs() = listOfTabs
    //</editor-fold>

}