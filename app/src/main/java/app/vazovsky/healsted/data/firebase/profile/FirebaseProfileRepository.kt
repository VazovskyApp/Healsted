package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseProfileRepository {

    /** Loyalty */
    fun addProfileLoyalty(uid: String, loyaltyProgress: LoyaltyProgress): Task<Void>
    fun fetchProfileLoyalty(uid: String): Task<DocumentSnapshot>

    /** Pill */
    fun addProfilePill(uid: String, pill: Pill): Task<Void>
    fun updateProfilePill(uid: String, pill: Pill): Task<Void>
    fun deleteProfilePill(uid: String, pill: Pill): Task<Void>
    fun fetchProfilePills(uid: String): Task<QuerySnapshot>

    /** Mood */
    fun addProfileMood(uid: String, mood: Mood): Task<Void>
    fun updateProfileMood(uid: String, mood: Mood): Task<Void>
    fun fetchProfileTodayMood(uid: String, date: Timestamp): Task<DocumentSnapshot>
    fun fetchProfileMoods(uid: String): Task<QuerySnapshot>

    /** Weight */
    fun addProfileMonitoringAttribute(uid: String, monitoringAttribute: MonitoringAttribute): Task<Void>
    fun fetchProfileWeight(uid: String): Task<QuerySnapshot>
    fun fetchProfileHeight(uid: String): Task<QuerySnapshot>
    fun fetchProfileTemperature(uid: String): Task<QuerySnapshot>
    fun fetchProfileBloodPressure(uid: String): Task<QuerySnapshot>
}