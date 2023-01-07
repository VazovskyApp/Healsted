package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepositoryImpl
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepositoryImpl
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.data.repository.AuthRepositoryImpl
import app.vazovsky.healsted.data.repository.DashboardRepository
import app.vazovsky.healsted.data.repository.DashboardRepositoryImpl
import app.vazovsky.healsted.data.repository.HealthRepository
import app.vazovsky.healsted.data.repository.HealthRepositoryImpl
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.data.repository.PillsRepositoryImpl
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
    abstract fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindPillsRepository(
        pillsRepository: PillsRepositoryImpl
    ): PillsRepository

    @Binds
    abstract fun bindDashboardRepository(
        dashboardRepository: DashboardRepositoryImpl
    ): DashboardRepository

    @Binds
    abstract fun bindHealthRepository(
        healthRepository: HealthRepositoryImpl
    ): HealthRepository

    @Binds
    abstract fun bindSettingsRepository(
        settingsRepository: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    abstract fun bindFirebaseAuthRepository(
        firebaseAuthRepository: FirebaseAuthRepositoryImpl
    ): FirebaseAuthRepository

    @Binds
    abstract fun bindFirebaseProfileRepository(
        firebaseProfileRepository: FirebaseProfileRepositoryImpl
    ): FirebaseProfileRepository
}