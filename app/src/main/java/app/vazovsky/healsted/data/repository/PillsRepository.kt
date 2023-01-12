package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.PillsTab

interface PillsRepository {

    //<editor-fold desc="Tabs">
    /** Получить табы для лекарств */
    fun getTabs(): List<PillsTab>
    //</editor-fold>

}