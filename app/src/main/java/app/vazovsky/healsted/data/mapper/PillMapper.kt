package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.room.entity.PillEntity
import app.vazovsky.healsted.managers.DateFormatter
import java.time.LocalTime
import javax.inject.Inject

/** Маппер для преобразования модели Pill в PillEntity и обратно */
class PillMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) {

    fun fromModelToEntity(model: Pill): PillEntity {
        return PillEntity(
            id = model.id,
            name = model.name,
            type = model.type,
            takePillType = model.takePillType,
            times = fromModelToEntityTime(model.times),
            datesTaken = model.datesTaken,
            datesTakenSelected = model.datesTakenSelected,
            startDate = dateFormatter.formatStringFromLocalDate(model.startDate),
            endDate = model.endDate?.let { dateFormatter.formatStringFromLocalDate(it) },
            amount = model.amount,
            comment = model.comment,
        )
    }

    fun fromEntityToModel(entity: PillEntity): Pill {
        return Pill(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            takePillType = entity.takePillType,
            times = fromEntityToModelTime(entity.times),
            datesTaken = entity.datesTaken,
            datesTakenSelected = entity.datesTakenSelected,
            startDate = dateFormatter.parseLocalDateFromString(entity.startDate),
            endDate = entity.endDate?.let { dateFormatter.parseLocalDateFromString(it) },
            amount = entity.amount,
            comment = entity.comment,
        )
    }

    fun fromDocumentToModel(document: PillDocument): Pill {
        return Pill(
            id = document.id,
            name = document.name,
            type = document.type,
            takePillType = document.takePillType,
            times = fromEntityToModelTime(document.times),
            datesTaken = document.datesTaken,
            datesTakenSelected = document.datesTakenSelected,
            startDate = dateFormatter.parseLocalDateFromString(document.startDate),
            endDate = document.endDate?.let { dateFormatter.parseLocalDateFromString(it) },
            amount = document.amount,
            comment = document.comment,
        )
    }

    fun fromModelToDocument(model: Pill): PillDocument {
        return PillDocument(
            id = model.id,
            name = model.name,
            type = model.type,
            takePillType = model.takePillType,
            times = fromModelToEntityTime(model.times),
            datesTaken = model.datesTaken,
            datesTakenSelected = model.datesTakenSelected,
            startDate = dateFormatter.formatStringFromLocalDate(model.startDate),
            endDate = model.endDate?.let { dateFormatter.formatStringFromLocalDate(it) },
            amount = model.amount,
            comment = model.comment,
        )
    }

    private fun fromEntityToModelTime(entityTime: Map<String, Boolean>): Map<LocalTime, Boolean> {
        val modelTime = mutableMapOf<LocalTime, Boolean>()
        entityTime.forEach { (time, isDone) ->
            modelTime[dateFormatter.parseLocalTimeFromString(time)] = isDone
        }
        return modelTime
    }

    private fun fromModelToEntityTime(modelTime: Map<LocalTime, Boolean>): Map<String, Boolean> {
        val entityTime = mutableMapOf<String, Boolean>()
        modelTime.forEach { (time, isDone) ->
            entityTime[dateFormatter.formatStringFromLocalTime(time)] = isDone
        }
        return entityTime
    }
}