package app.vazovsky.healsted.data.firebase.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import javax.inject.Inject

class FirebaseProfileRepositoryImpl @Inject constructor(
    private val firebaseProfileService: FirebaseProfileService,
) : FirebaseProfileRepository {
    override fun addProfileLoyalty(
        uid: String,
        loyaltyProgress: LoyaltyProgress,
    ) = firebaseProfileService.addProfileLoyalty(uid, loyaltyProgress)

    override fun addProfilePills(
        uid: String,
        listPills: Map<String, Pill>,
    ) = firebaseProfileService.addProfilePills(uid, listPills)

    override fun addProfileMoods(
        uid: String,
        listMoods: List<Mood>,
    ) = firebaseProfileService.addProfileMoods(uid, listMoods)

    override fun fetchProfileLoyalty(uid: String) = firebaseProfileService.fetchProfileLoyalty(uid)

    override fun fetchProfilePills(uid: String) = firebaseProfileService.fetchProfilePills(uid)
    override fun fetchProfileMoods(uid: String) = firebaseProfileService.fetchProfileMoods(uid)
}