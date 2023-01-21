package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepository
import app.vazovsky.healsted.data.appinfo.repository.AppInfoRepositoryImpl
import app.vazovsky.healsted.data.appinfo.repository.BuildConfigProvider
import app.vazovsky.healsted.data.appinfo.repository.BuildConfigProviderImpl
import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepositoryImpl
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepositoryImpl
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.data.repository.AuthRepositoryImpl
import app.vazovsky.healsted.data.repository.PillsRepository
import app.vazovsky.healsted.data.repository.PillsRepositoryImpl
import app.vazovsky.healsted.data.repository.RoomRepository
import app.vazovsky.healsted.data.repository.RoomRepositoryImpl
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

    @Binds
    abstract fun bindRoomRepository(
        roomRepository: RoomRepositoryImpl
    ): RoomRepository

    @Binds
    abstract fun bindAppInfoRepository(
        appInfoRepository: AppInfoRepositoryImpl
    ): AppInfoRepository

}