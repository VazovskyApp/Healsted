package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillsTab
import app.vazovsky.mypills.data.model.PillsTabSlot

interface PillsRepository {
    fun getTabs(): List<PillsTab>
    fun getPills(slot: PillsTabSlot?): List<Pill>
}