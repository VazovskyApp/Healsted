package app.vazovsky.healsted.extensions

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill

fun Account?.orDefault() = this ?: Account()
fun Account?.orError() = this ?: throw Exception()

fun LoyaltyProgress?.orDefault() = this ?: LoyaltyProgress()
fun LoyaltyProgress?.orError() = this ?: throw Exception()

fun Mood?.orDefault() = this ?: Mood()
fun Mood?.orError() = this ?: throw Exception()

fun Pill?.orDefault() = this ?: Pill()
fun Pill?.orError() = this ?: throw Exception()