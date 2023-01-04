package app.vazovsky.healsted.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.Phone
import java.time.LocalDate
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    override fun getProfile(): Account {
        return Account(
            id = "0",
            nickname = "Vazovsky",
            phoneNumber = Phone(7, "9910212814", "7"),
            birthday = LocalDate.of(1999, 2, 17),
            level = AccountLevel.GOD,
        )
    }
}