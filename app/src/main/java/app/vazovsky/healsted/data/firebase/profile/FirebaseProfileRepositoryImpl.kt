package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.firebase.model.MonitoringAttributeDocument
import app.vazovsky.healsted.data.firebase.model.MoodDocument
import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.model.LoyaltyProgress
import javax.inject.Inject

class FirebaseProfileRepositoryImpl @Inject constructor(
    private val firebaseProfileService: FirebaseProfileService,
) : FirebaseProfileRepository {

    //<editor-fold desc="Loyalty">
    override fun addProfileLoyalty(uid: String, loyaltyProgress: LoyaltyProgress) =
        firebaseProfileService.addProfileLoyalty(uid, loyaltyProgress)

    override fun fetchProfileLoyalty(uid: String) = firebaseProfileService.fetchProfileLoyalty(uid)
    //</editor-fold>

    //<editor-fold desc="Pills">
    override fun addProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firebaseProfileService.addProfilePill(uid, pill)

    override fun updateProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firebaseProfileService.updateProfilePill(uid, pill)

    override fun deleteProfilePill(
        uid: String,
        pill: PillDocument,
    ) = firebaseProfileService.deleteProfilePill(uid, pill)

    override fun fetchProfilePills(uid: String) = firebaseProfileService.fetchProfilePills(uid)
    //</editor-fold>

    //<editor-fold desc="Mood">
    override fun addProfileMood(
        uid: String,
        mood: MoodDocument,
    ) = firebaseProfileService.addProfileMood(uid, mood)

    override fun updateProfileMood(
        uid: String,
        mood: MoodDocument,
    ) = firebaseProfileService.updateProfileMood(uid, mood)

    override fun fetchProfileTodayMood(
        uid: String,
        date: String,
    ) = firebaseProfileService.fetchProfileTodayMood(uid, date)

    override fun fetchProfileMoods(uid: String) = firebaseProfileService.fetchProfileMoods(uid)
    //</editor-fold>

    //<editor-fold desc="Health">
    override fun addProfileMonitoringAttribute(
        uid: String,
        monitoringAttribute: MonitoringAttributeDocument,
    ) = firebaseProfileService.addProfileMonitoringAttribute(uid, monitoringAttribute)

    override fun fetchProfileWeight(uid: String) = firebaseProfileService.fetchProfileWeight(uid)
    override fun fetchProfileHeight(uid: String) = firebaseProfileService.fetchProfileHeight(uid)
    override fun fetchProfileTemperature(uid: String) = firebaseProfileService.fetchProfileTemperature(uid)
    override fun fetchProfileBloodPressure(uid: String) = firebaseProfileService.fetchProfileBloodPressure(uid)
    //</editor-fold>

}