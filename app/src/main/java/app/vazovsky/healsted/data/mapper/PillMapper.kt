package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.convertDatesTakenSelectedFromString
import app.vazovsky.healsted.data.model.convertDatesTakenTypeFromString
import app.vazovsky.healsted.data.model.convertPillTypeFromString
import app.vazovsky.healsted.data.model.convertTakePillTypeFromString
import app.vazovsky.healsted.extensions.orDefault
import java.time.OffsetDateTime
import java.time.OffsetTime
import javax.inject.Inject

/** TODO Сделать адекватнее */
@Suppress("UNCHECKED_CAST")
class PillMapper @Inject constructor() {
    fun fromMapToModel(map: Map<*, *>): Pill {
        return Pill(
            id = (map["id"] as String?).orDefault(),
            name = (map["name"] as String?).orDefault(),
            type = (map["type"] as String?).orDefault().convertPillTypeFromString(),
            takePillType = (map["takePillType"] as String?).orDefault().convertTakePillTypeFromString(),
            times = (map["times"] as List<OffsetTime>?).orDefault(),
            datesTaken = (map["datesTaken"] as String?).orDefault().convertDatesTakenTypeFromString(),
            datesTakenSelected = (map["datesTakenSelected"] as List<String>?).orEmpty()
                .map { it.convertDatesTakenSelectedFromString() },
            startDate = fromMapToOffsetDateTime(map["startDate"] as Map<*, *>),
            endDate = map["endDate"]?.let { fromMapToOffsetDateTime(it as Map<*, *>) },
            amount = (map["amount"] as Double?)?.toFloat().orDefault(),
        )
    }

    private fun fromMapToOffsetDateTime(dateMap: Map<*, *>): OffsetDateTime {
        return OffsetDateTime.of(
            (dateMap["year"] as Long?)?.toInt().orDefault(),
            (dateMap["monthValue"] as Long?)?.toInt().orDefault(1),
            (dateMap["dayOfMonth"] as Long?)?.toInt().orDefault(1),
            (dateMap["hour"] as Long?)?.toInt().orDefault(0),
            (dateMap["minute"] as Long?)?.toInt().orDefault(0),
            (dateMap["second"] as Long?)?.toInt().orDefault(0),
            (dateMap["nano"] as Long?)?.toInt().orDefault(0),
            OffsetDateTime.now().offset,
        )
    }
}