package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Pill
import javax.inject.Inject

class FirebaseProfileRepositoryImpl @Inject constructor(
    private val firebaseProfileService: FirebaseProfileService,
) : FirebaseProfileRepository {
    override fun addProfileLoyalty(
        email: String,
        loyaltyProgress: LoyaltyProgress
    ) = firebaseProfileService.addProfileLoyalty(email, loyaltyProgress)

    override fun addProfilePills(
        email: String,
        listPills: List<Pill>
    ) = firebaseProfileService.addProfilePills(email, listPills)

    override fun fetchProfileLoyalty(email: String) = firebaseProfileService.fetchProfileLoyalty(email)

    override fun fetchProfilePills(email: String) = firebaseProfileService.fetchProfilePills(email)
}