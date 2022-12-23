package app.vazovsky.mypills.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Номер телефона
 */
@Parcelize
data class Phone(
    /** Телефонный код страны */
    @SerializedName("countryCode") val countryCode: Int,
    /** Часть номера без кода страны */
    @SerializedName("nationalNumber") var nationalNumber: String,
    /** Код страны ISO 3166-1 alpha-2 */
    @SerializedName("isoCode") val isoCode: String,
) : Parcelable {
    init {
        nationalNumber = nationalNumber.normalizePhone()
    }

    /** Удаляем разделители */
    private fun String.normalizePhone(): String {
        return this.replace(" ", "")
    }

    companion object {
        const val DEFAULT_COUNTRY_CODE = 7
        const val DEFAULT_ISO_CODE = "RU"

        /** Номер телефона из nationalNumber с дефолтным кодом страны */
        fun fromNationalNumber(nationalNumber: String): Phone {
            return Phone(
                DEFAULT_COUNTRY_CODE,
                nationalNumber,
                DEFAULT_ISO_CODE
            )
        }
    }
}
