package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

/** Пользователь */
@Parcelize
data class User(
    /** Почта */
    @PropertyName("email") val email: String = "",

    /** Номер телефона */
    @PropertyName("phoneNumber") var phoneNumber: String = "",
) : Parcelable