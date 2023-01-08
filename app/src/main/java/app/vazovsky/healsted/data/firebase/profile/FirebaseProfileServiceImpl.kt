package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val LOYALTY_COLLECTION = "loyalty"
const val PILLS_COLLECTION = "pills"
const val MOODS_COLLECTION = "moods"
const val HEALTH_COLLECTION = "health"

class FirebaseProfileServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseProfileService {
    override fun addProfileLoyalty(
        uid: String,
        loyaltyProgress: LoyaltyProgress,
    ) = firestore.collection(LOYALTY_COLLECTION).document(uid).set(loyaltyProgress)

    override fun fetchProfileLoyalty(
        uid: String,
    ) = firestore.collection(LOYALTY_COLLECTION).document(uid).get()

    override fun addProfilePill(
        uid: String,
        pill: Pill,
    ) = firestore.collection(PILLS_COLLECTION).document(uid).collection(PILLS_COLLECTION).document(pill.name).set(pill)

    override fun fetchProfilePills(
        uid: String,
    ) = firestore.collection(PILLS_COLLECTION).document(uid).collection(PILLS_COLLECTION).get()

    override fun addProfileMood(
        uid: String,
        mood: Mood,
    ) = firestore.collection(MOODS_COLLECTION).document(uid).collection(MOODS_COLLECTION)
        .document(mood.date.toString()).set(mood)

    override fun fetchProfileMoods(
        uid: String,
    ) = firestore.collection(MOODS_COLLECTION).document(uid).collection(MOODS_COLLECTION).get()

    override fun addProfileMonitoringAttribute(
        uid: String,
        monitoringAttribute: MonitoringAttribute,
    ) = firestore.collection(HEALTH_COLLECTION).document(uid).collection(monitoringAttribute.type.toString())
        .document(monitoringAttribute.date.toString()).set(monitoringAttribute)

    override fun fetchProfileWeight(uid: String) =
        firestore.collection(HEALTH_COLLECTION).document(uid).collection(MonitoringType.WEIGHT.toString()).get()

    override fun fetchProfileHeight(uid: String) =
        firestore.collection(HEALTH_COLLECTION).document(uid).collection(MonitoringType.HEIGHT.toString()).get()

    override fun fetchProfileTemperature(uid: String) =
        firestore.collection(HEALTH_COLLECTION).document(uid).collection(MonitoringType.TEMPERATURE.toString()).get()

    override fun fetchProfileBloodPressure(uid: String) =
        firestore.collection(HEALTH_COLLECTION).document(uid).collection(MonitoringType.BLOOD_PRESSURE.toString()).get()
}