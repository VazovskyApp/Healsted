package app.vazovsky.healsted.data.repository

import app.vazovsky.healsted.data.model.Account

interface ProfileRepository {
    fun getProfile(): Account
}