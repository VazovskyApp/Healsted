package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.repository.DashboardRepository
import app.vazovsky.healsted.data.repository.DashboardRepositoryImpl
import app.vazovsky.healsted.data.repository.HealthRepository
import app.vazovsky.healsted.data.repository.HealthRepositoryImpl
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.data.repository.PillsRepositoryImpl
import app.vazovsky.healsted.data.repository.ProfileRepository
import app.vazovsky.healsted.data.repository.ProfileRepositoryImpl
import app.vazovsky.healsted.data.repository.SettingsRepository
import app.vazovsky.healsted.data.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Модуль для репозиториев */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindPillsRepository(pillsRepository: PillsRepositoryImpl): PillsRepository

    @Binds
    abstract fun bindDashboardRepository(dashboardRepository: DashboardRepositoryImpl): DashboardRepository

    @Binds
    abstract fun bindHealthRepository(healthRepository: HealthRepositoryImpl): HealthRepository

    @Binds
    abstract fun bindSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository
}