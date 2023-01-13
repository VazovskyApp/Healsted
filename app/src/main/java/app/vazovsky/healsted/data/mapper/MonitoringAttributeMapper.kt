package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.firebase.model.MonitoringAttributeDocument
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.managers.DateFormatter
import javax.inject.Inject

class MonitoringAttributeMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) {
    fun fromDocumentToModel(document: MonitoringAttributeDocument): MonitoringAttribute {
        return MonitoringAttribute(
            value = document.value,
            type = document.type,
            date = dateFormatter.parseLocalDateFromString(document.date),
        )
    }

    fun fromModelToDocument(model: MonitoringAttribute): MonitoringAttributeDocument {
        return MonitoringAttributeDocument(
            value = model.value,
            type = model.type,
            date = dateFormatter.formatStringFromLocalDate(model.date),
        )
    }
}