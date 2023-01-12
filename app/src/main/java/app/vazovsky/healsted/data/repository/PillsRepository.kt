package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.data.model.PillsTab

interface PillsRepository {

    //<editor-fold desc="Tabs">
    /** Получить табы для лекарств */
    fun getTabs(): List<PillsTab>
    //</editor-fold>

    //<editor-fold desc="PillTypes">
    /** Получить типы лекарств */
    fun getPillsTypes(): List<PillTypeItem>
    //</editor-fold>

}