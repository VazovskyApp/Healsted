package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.PillsTab

interface PillsRepository {
    fun getTabs(): List<PillsTab>
}