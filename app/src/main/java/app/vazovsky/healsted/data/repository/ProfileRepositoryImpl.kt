package app.vazovsky.healsted.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.AccountLevel
import app.vazovsky.healsted.data.model.Phone
import app.vazovsky.healsted.data.model.User
import java.time.LocalDate
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    override fun getProfile(): Account {
        return Account(
            accountHolder = User(email = "", phoneNumber = ""),
            nickname = "Vazovsky",
            birthday = LocalDate.of(1999, 2, 17),
        )
    }
}