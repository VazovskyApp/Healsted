package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// TODO пока не нужно
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

    /** Удаление разделителей */
    private fun String.normalizePhone() = this.replace(" ", "")

    companion object {
        const val DEFAULT_COUNTRY_CODE = 7
        const val DEFAULT_ISO_CODE = "RU"

        /** Номер телефона из nationalNumber с дефолтным кодом страны */
        fun fromNationalNumber(nationalNumber: String) = Phone(
            countryCode = DEFAULT_COUNTRY_CODE,
            nationalNumber = nationalNumber,
            isoCode = DEFAULT_ISO_CODE,
        )
    }
}
