package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Pill
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val LOYALTY_COLLECTION = "loyalty"
const val PILLS_COLLECTION = "pills"

class FirebaseProfileServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseProfileService {
    override fun addProfileLoyalty(
        email: String,
        loyaltyProgress: LoyaltyProgress
    ) = firestore.collection(LOYALTY_COLLECTION).document(email).set(loyaltyProgress)

    override fun addProfilePills(
        email: String,
        listPills: List<Pill>
    ) = firestore.collection(PILLS_COLLECTION).document(email).set(listPills)

    override fun fetchProfileLoyalty(
        email: String
    ) = firestore.collection(LOYALTY_COLLECTION).document(email).get()

    override fun fetchProfilePills(
        email: String
    ) = firestore.collection(PILLS_COLLECTION).document(email).get()
}