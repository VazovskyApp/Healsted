package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface FirebaseProfileService {
    fun addProfileLoyalty(email: String, loyaltyProgress: LoyaltyProgress): Task<Void>
    fun addProfilePills(email: String, listPills: List<Pill>): Task<Void>
    fun fetchProfileLoyalty(email: String): Task<DocumentSnapshot>
    fun fetchProfilePills(email: String): Task<DocumentSnapshot>
}