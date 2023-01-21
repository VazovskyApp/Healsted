package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/** Пользователь */
@Parcelize
data class User(
    /** Почта */
    @SerializedName("email") @PropertyName("email") val email: String = "",

    /** Номер телефона */
    @SerializedName("phoneNumber") @PropertyName("phoneNumber") var phoneNumber: String = "",
) : Parcelable