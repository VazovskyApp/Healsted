package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.PillTypeItem
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

    //<editor-fold desc="PillTypes">
    private val listOfPillTypes = listOf(
        PillTypeItem(
            PillType.TABLETS,
            R.drawable.ic_pill_tablets,
        ),
        PillTypeItem(
            PillType.CAPSULE,
            R.drawable.ic_pill_capsule,
        ),
        PillTypeItem(
            PillType.INJECTION,
            R.drawable.ic_pill_injection,
        ),
        PillTypeItem(
            PillType.PROCEDURES,
            R.drawable.ic_pill_procedures,
        ),
        PillTypeItem(
            PillType.DROPS,
            R.drawable.ic_pill_drops,
        ),
        PillTypeItem(
            PillType.LIQUID,
            R.drawable.ic_pill_liquid,
        ),
        PillTypeItem(
            PillType.CREAM,
            R.drawable.ic_pill_cream,
        ),
        PillTypeItem(
            PillType.SPRAY,
            R.drawable.ic_pill_spray,
        ),
    )

    override fun getPillsTypes(): List<PillTypeItem> {
        return listOfPillTypes
    }
    //</editor-fold>

}