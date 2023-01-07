package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseProfileService {
    fun addProfileLoyalty(uid: String, loyaltyProgress: LoyaltyProgress): Task<Void>

    fun addProfilePills(uid: String, listPills: Map<String, Pill>): Task<Void>

    fun addProfileMoods(uid: String, listMoods: List<Mood>): Task<Void>

    fun fetchProfileLoyalty(uid: String): Task<DocumentSnapshot>

    fun fetchProfilePills(uid: String): Task<DocumentSnapshot>

    fun fetchProfileMoods(uid: String): Task<QuerySnapshot>
}