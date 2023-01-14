package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.room.entity.PillEntity
import app.vazovsky.healsted.managers.DateFormatter
import java.time.LocalDateTime
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
            history = fromModelToEntityHistory(model.history),
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
            history = fromEntityMapToModelHistory(entity.history),
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
            history = fromEntityMapToModelHistory(document.history),
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
            history = fromModelToEntityHistory(model.history),
        )
    }

    fun fromEntityToModelTime(entityTime: Map<String, String>): Map<String, LocalTime> {
        val modelTime = mutableMapOf<String, LocalTime>()
        entityTime.forEach { (index, time) ->
            modelTime[index] = dateFormatter.parseLocalTimeFromString(time)
        }
        return modelTime
    }

    fun fromModelToEntityTime(modelTime: Map<String, LocalTime>): Map<String, String> {
        val entityTime = mutableMapOf<String, String>()
        modelTime.forEach { (index, time) ->
            entityTime[index] = dateFormatter.formatStringFromLocalTime(time)
        }
        return entityTime
    }

    fun fromEntityMapToModelHistory(entityHistory: Map<String, String>): Map<LocalDateTime, LocalTime> {
        val modelHistory = mutableMapOf<LocalDateTime, LocalTime>()
        entityHistory.forEach { (dateTime, time) ->
            modelHistory[dateFormatter.parseLocalDateTimeFromString(dateTime)] = dateFormatter.parseLocalTimeFromString(time)
        }
        return modelHistory
    }

    fun fromModelToEntityHistory(modelHistory: Map<LocalDateTime, LocalTime>): Map<String, String> {
        val entityHistory = mutableMapOf<String, String>()
        modelHistory.forEach { (dateTime, time) ->
            entityHistory[dateFormatter.formatStringFromLocalDateTime(dateTime)] = dateFormatter.formatStringFromLocalTime(time)
        }
        return entityHistory
    }

}