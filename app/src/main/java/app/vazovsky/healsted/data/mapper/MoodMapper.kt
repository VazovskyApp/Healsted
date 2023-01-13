package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.firebase.model.MoodDocument
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.managers.DateFormatter
import javax.inject.Inject

class MoodMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) {
    fun fromDocumentToModel(document: MoodDocument): Mood {
        return Mood(
            value = document.value,
            date = dateFormatter.parseLocalDateFromString(document.date),
        )
    }

    fun fromModelToDocument(model: Mood): MoodDocument {
        return MoodDocument(
            value = model.value,
            date = dateFormatter.formatStringFromLocalDate(model.date),
        )
    }
}