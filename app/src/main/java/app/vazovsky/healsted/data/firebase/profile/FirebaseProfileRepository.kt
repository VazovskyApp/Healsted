package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface FirebaseProfileRepository {
    fun addProfileLoyalty(uid: String, loyaltyProgress: LoyaltyProgress): Task<Void>
    fun addProfilePills(uid: String, listPills: Map<String, Pill>): Task<Void>
    fun fetchProfileLoyalty(uid: String): Task<DocumentSnapshot>
    fun fetchProfilePills(uid: String): Task<DocumentSnapshot>
}