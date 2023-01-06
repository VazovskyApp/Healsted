package app.vazovsky.healsted.data.repository

interface AuthRepository {
    fun getIsAuthorized(): Boolean
    fun setIsAuthorized(isAuthorized: Boolean)

    fun getIsOnBoardingShowed(): Boolean
    fun setIsOnBoardingShowed(isOnBoardingShowed: Boolean)

    fun getCurrentUserUid(): String?
    fun setCurrentUserUid(userUid: String?)
}