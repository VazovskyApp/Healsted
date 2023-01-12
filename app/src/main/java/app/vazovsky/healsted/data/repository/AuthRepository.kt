package app.vazovsky.healsted.data.repository

interface AuthRepository {

    //<editor-fold desc="Authorized">
    /** Авторизован ли аккаунт */
    fun getIsAuthorized(): Boolean

    /** Установка авторизации аккаунта */
    fun setIsAuthorized(isAuthorized: Boolean)
    //</editor-fold>

    //<editor-fold desc="OnBoarding Showed">
    /** Был ли просмотрен онбординг */
    fun getIsOnBoardingShowed(): Boolean

    /** Установка просмотра онбординга */
    fun setIsOnBoardingShowed(isOnBoardingShowed: Boolean)
    //</editor-fold>

    //<editor-fold desc="Current User UID">
    /** Получение UID текущего пользователя */
    fun getCurrentUserUid(): String?

    /** Установка текущего UID пользователя */
    fun setCurrentUserUid(userUid: String?)
    //</editor-fold>

}