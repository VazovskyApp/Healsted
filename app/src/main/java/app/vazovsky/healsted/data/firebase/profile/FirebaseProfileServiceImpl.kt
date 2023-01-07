package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

const val LOYALTY_COLLECTION = "loyalty"
const val PILLS_COLLECTION = "pills"
const val MOODS_COLLECTION = "moods"

class FirebaseProfileServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseProfileService {
    override fun addProfileLoyalty(
        uid: String,
        loyaltyProgress: LoyaltyProgress,
    ) = firestore.collection(LOYALTY_COLLECTION).document(uid).set(loyaltyProgress)

    override fun addProfilePills(
        uid: String,
        listPills: Map<String, Pill>,
    ) = firestore.collection(PILLS_COLLECTION).document(uid).set(listPills)

    override fun addProfileMoods(
        uid: String,
        listMoods: List<Mood>,
    ) = firestore.collection(MOODS_COLLECTION).document(uid).set(listMoods)

    override fun fetchProfileLoyalty(
        uid: String,
    ) = firestore.collection(LOYALTY_COLLECTION).document(uid).get()

    override fun fetchProfilePills(
        uid: String,
    ) = firestore.collection(PILLS_COLLECTION).document(uid).get()

    override fun fetchProfileMoods(uid: String): Task<QuerySnapshot> {
        TODO("Not yet implemented")
    }
}