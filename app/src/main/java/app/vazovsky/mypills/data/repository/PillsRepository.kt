package app.vazovsky.mypills.data.repository

import app.vazovsky.mypills.data.model.Pill

interface PillsRepository {
    fun getPills(): List<Pill>
}