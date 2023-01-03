package app.vazovsky.mypills.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillType
import java.time.LocalDate
import javax.inject.Inject

class PillsRepositoryImpl @Inject constructor() : PillsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPills(): List<Pill> {
        //return emptyList()
        return listOf(
            Pill(
                id = "0",
                name = "Нурофен",
                type = PillType.CAPSULE,
                dates = null,
                amount = 1F,
                startDate = LocalDate.now(),
            )
        )
    }
}