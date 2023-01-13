package app.vazovsky.healsted.extensions

import app.vazovsky.healsted.data.firebase.model.AccountDocument
import app.vazovsky.healsted.data.firebase.model.MonitoringAttributeDocument
import app.vazovsky.healsted.data.firebase.model.MoodDocument
import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.data.model.MonitoringAttribute
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

fun MonitoringAttribute?.orDefault() = this ?: MonitoringAttribute()
fun MonitoringAttribute?.orError() = this ?: throw Exception()

fun PillDocument?.orDefault() = this ?: PillDocument()
fun PillDocument?.orError() = this ?: throw Exception()

fun AccountDocument?.orDefault() = this ?: AccountDocument()
fun AccountDocument?.orError() = this ?: throw Exception()

fun MonitoringAttributeDocument?.orDefault() = this ?: MonitoringAttributeDocument()
fun MonitoringAttributeDocument?.orError() = this ?: throw Exception()

fun MoodDocument?.orDefault() = this ?: MoodDocument()
fun MoodDocument?.orError() = this ?: throw Exception()
