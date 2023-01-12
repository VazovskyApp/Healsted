package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.room.entity.PillEntity
import app.vazovsky.healsted.managers.DateFormatter
import javax.inject.Inject

class PillMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) {
    fun fromModelToEntity(model: Pill): PillEntity {
        return PillEntity(
            id = model.id,
            name = model.name,
            type = model.type,
            takePillType = model.takePillType,
            times = model.times.map { it.toString() },
            datesTaken = model.datesTaken,
            datesTakenSelected = model.datesTakenSelected,
            startDate = model.startDate.toString(),
            endDate = model.endDate?.toString(),
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
            times = entity.times.map { dateFormatter.parseTimeFromString(it) },
            datesTaken = entity.datesTaken,
            datesTakenSelected = entity.datesTakenSelected,
            startDate = dateFormatter.parseDateFromString(entity.startDate),
            endDate = entity.endDate?.let { dateFormatter.parseDateFromString(it) },
            amount = entity.amount,
            comment = entity.comment,
        )
    }
}