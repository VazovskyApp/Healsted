package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot

interface PillsRepository {
    fun getTabs(): List<PillsTab>
    fun getPills(slot: PillsTabSlot?): List<Pill>
}