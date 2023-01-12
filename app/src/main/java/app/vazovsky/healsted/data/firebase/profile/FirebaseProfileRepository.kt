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

    //<editor-fold desc="Loyalty">
    /** Создать программу лояльности в FireStore */
    fun addProfileLoyalty(uid: String, loyaltyProgress: LoyaltyProgress): Task<Void>

    /** Получить программу лояльности из FireStore */
    fun fetchProfileLoyalty(uid: String): Task<DocumentSnapshot>
    //</editor-fold>

    //<editor-fold desc="Pills">
    /** Добавить лекарство в FireStore */
    fun addProfilePill(uid: String, pill: Pill): Task<Void>

    /** Обновить лекарство в FireStore */
    fun updateProfilePill(uid: String, pill: Pill): Task<Void>

    /** Удалить лекарство из FireStore */
    fun deleteProfilePill(uid: String, pill: Pill): Task<Void>

    /** Получить лекарства из FireStore */
    fun fetchProfilePills(uid: String): Task<QuerySnapshot>
    //</editor-fold>

    //<editor-fold desc="Mood">
    /** Добавить настроение в FireStore */
    fun addProfileMood(uid: String, mood: Mood): Task<Void>

    /** Обновить настроение в FireStore */
    fun updateProfileMood(uid: String, mood: Mood): Task<Void>

    /** Получить сегодняшнее настроение из FireStore */
    fun fetchProfileTodayMood(uid: String, date: Timestamp): Task<DocumentSnapshot>

    /** Получить все настроения из FireStore */
    fun fetchProfileMoods(uid: String): Task<QuerySnapshot>
    //</editor-fold>

    //<editor-fold desc="Health">
    /** Добавить информацию об атрибуте мониторинга здоровья в FireStore */
    fun addProfileMonitoringAttribute(uid: String, monitoringAttribute: MonitoringAttribute): Task<Void>

    /** Получить информацию об весе из FireStore */
    fun fetchProfileWeight(uid: String): Task<QuerySnapshot>

    /** Получить информацию о росте из FireStore */
    fun fetchProfileHeight(uid: String): Task<QuerySnapshot>

    /** Получить информацию о температуре из FireStore */
    fun fetchProfileTemperature(uid: String): Task<QuerySnapshot>

    /** Получить информацию о кровеносном давлении из FireStore */
    fun fetchProfileBloodPressure(uid: String): Task<QuerySnapshot>
    //</editor-fold>

}