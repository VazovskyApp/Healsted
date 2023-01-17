package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.firebase.model.MonitoringAttributeDocument
import app.vazovsky.healsted.data.firebase.model.MoodDocument
import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.extensions.serializeToMap
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val LOYALTY_COLLECTION = "loyalty"
const val PILLS_COLLECTION = "pills"
const val MOODS_COLLECTION = "moods"
const val HEALTH_COLLECTION = "health"

const val PILL_ID = "id"
const val PILL_NAME = "name"
const val PILL_TYPE = "type"
const val PILL_TAKE_PILL_TYPE = "takePillType"
const val PILL_TIMES = "times"
const val PILL_DATES_TAKEN = "datesTaken"
const val PILL_DATES_TAKEN_SELECTED = "datesTakenSelected"
const val PILL_START_DATE = "startDate"
const val PILL_END_DATE = "endDate"
const val PILL_AMOUNT = "amount"
const val PILL_COMMENT = "comment"
const val PILL_HISTORY = "history"

class FirebaseProfileServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseProfileService {

    //<editor-fold desc="Loyalty">
    override fun addProfileLoyalty(
        uid: String,
        loyaltyProgress: LoyaltyProgress,
    ) = firestore.collection(LOYALTY_COLLECTION)
        .document(uid)
        .set(loyaltyProgress)

    override fun fetchProfileLoyalty(
        uid: String,
    ) = firestore.collection(LOYALTY_COLLECTION)
        .document(uid).get()
    //</editor-fold>

    //<editor-fold desc="Pills">
    override fun addProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firestore.collection(PILLS_COLLECTION)
        .document(uid)
        .collection(PILLS_COLLECTION)
        .document(pill.id)
        .set(pill)

    override fun updateProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firestore.collection(PILLS_COLLECTION)
        .document(uid)
        .collection(PILLS_COLLECTION)
        .document(pill.id)
        .update(
            mapOf(
                PILL_ID to pill.id,
                PILL_NAME to pill.name,
                PILL_TYPE to pill.type,
                PILL_TAKE_PILL_TYPE to pill.takePillType,
                PILL_TIMES to pill.times,
                PILL_DATES_TAKEN to pill.datesTaken,
                PILL_DATES_TAKEN_SELECTED to pill.datesTakenSelected,
                PILL_START_DATE to pill.startDate,
                PILL_END_DATE to pill.endDate,
                PILL_AMOUNT to pill.amount,
                PILL_COMMENT to pill.comment,
                PILL_HISTORY to pill.history,
            )
        )

    override fun deleteProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firestore.collection(PILLS_COLLECTION)
        .document(uid)
        .collection(PILLS_COLLECTION)
        .document(pill.id).delete()

    override fun fetchProfilePills(
        uid: String,
    ) = firestore.collection(PILLS_COLLECTION)
        .document(uid)
        .collection(PILLS_COLLECTION).get()
    //</editor-fold>

    //<editor-fold desc="Mood">
    override fun addProfileMood(
        uid: String,
        mood: MoodDocument,
    ) = firestore.collection(MOODS_COLLECTION)
        .document(uid)
        .collection(MOODS_COLLECTION)
        .document(mood.date)
        .set(mood)

    override fun updateProfileMood(
        uid: String,
        mood: MoodDocument,
    ) = firestore.collection(MOODS_COLLECTION)
        .document(uid)
        .collection(MOODS_COLLECTION)
        .document(mood.date)
        .update(mood.serializeToMap())

    override fun fetchProfileTodayMood(
        uid: String,
        date: String,
    ) = firestore.collection(MOODS_COLLECTION)
        .document(uid)
        .collection(MOODS_COLLECTION)
        .document(date).get()

    override fun fetchProfileMoods(
        uid: String,
    ) = firestore.collection(MOODS_COLLECTION)
        .document(uid)
        .collection(MOODS_COLLECTION).get()
    //</editor-fold>

    //<editor-fold desc="Health">
    override fun addProfileMonitoringAttribute(
        uid: String,
        monitoringAttribute: MonitoringAttributeDocument,
    ) = firestore.collection(HEALTH_COLLECTION)
        .document(uid)
        .collection(monitoringAttribute.type.name)
        .document(monitoringAttribute.date)
        .set(monitoringAttribute)

    override fun fetchProfileWeight(
        uid: String,
    ) = firestore.collection(HEALTH_COLLECTION)
        .document(uid)
        .collection(MonitoringType.WEIGHT.name).get()

    override fun fetchProfileHeight(
        uid: String,
    ) = firestore.collection(HEALTH_COLLECTION)
        .document(uid)
        .collection(MonitoringType.HEIGHT.name).get()

    override fun fetchProfileTemperature(
        uid: String,
    ) = firestore.collection(HEALTH_COLLECTION)
        .document(uid)
        .collection(MonitoringType.TEMPERATURE.name).get()

    override fun fetchProfileBloodPressure(
        uid: String,
    ) = firestore.collection(HEALTH_COLLECTION)
        .document(uid)
        .collection(MonitoringType.BLOOD_PRESSURE.name).get()
    //</editor-fold>

}