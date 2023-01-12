package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthService
import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthServiceImpl
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileService
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Модуль для сервисов */
@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindFirebaseAuthService(
        firebaseAuthService: FirebaseAuthServiceImpl,
    ): FirebaseAuthService

    @Binds
    abstract fun bindFirebaseProfileService(
        firebaseProfileService: FirebaseProfileServiceImpl,
    ): FirebaseProfileService
}