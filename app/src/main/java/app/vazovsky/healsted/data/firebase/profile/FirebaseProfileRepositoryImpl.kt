package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import javax.inject.Inject

class FirebaseProfileRepositoryImpl @Inject constructor(
    private val firebaseProfileService: FirebaseProfileService,
) : FirebaseProfileRepository {
    override fun addProfileLoyalty(
        uid: String,
        loyaltyProgress: LoyaltyProgress,
    ) = firebaseProfileService.addProfileLoyalty(uid, loyaltyProgress)

    override fun fetchProfileLoyalty(uid: String) = firebaseProfileService.fetchProfileLoyalty(uid)

    override fun addProfilePill(
        uid: String,
        pill: Pill,
    ) = firebaseProfileService.addProfilePill(uid, pill)

    override fun updateProfilePill(
        uid: String,
        pill: Pill,
    ) = firebaseProfileService.updateProfilePill(uid, pill)

    override fun deleteProfilePill(
        uid: String,
        pill: Pill,
    ) = firebaseProfileService.deleteProfilePill(uid,pill)

    override fun fetchProfilePills(uid: String) = firebaseProfileService.fetchProfilePills(uid)

    override fun addProfileMood(
        uid: String,
        mood: Mood,
    ) = firebaseProfileService.addProfileMood(uid, mood)

    override fun updateProfileMood(
        uid: String,
        mood: Mood,
    ) = firebaseProfileService.updateProfileMood(uid, mood)

    override fun fetchProfileTodayMood(
        uid: String,
        date: Timestamp,
    ) = firebaseProfileService.fetchProfileTodayMood(uid, date)

    override fun fetchProfileMoods(uid: String) = firebaseProfileService.fetchProfileMoods(uid)

    override fun addProfileMonitoringAttribute(
        uid: String,
        monitoringAttribute: MonitoringAttribute,
    ) = firebaseProfileService.addProfileMonitoringAttribute(uid, monitoringAttribute)

    override fun fetchProfileWeight(uid: String) = firebaseProfileService.fetchProfileWeight(uid)
    override fun fetchProfileHeight(uid: String) = firebaseProfileService.fetchProfileHeight(uid)
    override fun fetchProfileTemperature(uid: String) = firebaseProfileService.fetchProfileTemperature(uid)
    override fun fetchProfileBloodPressure(uid: String) = firebaseProfileService.fetchProfileBloodPressure(uid)
}