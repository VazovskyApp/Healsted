package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.firebase.FirebaseService
import app.vazovsky.healsted.data.firebase.FirebaseServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindFirebaseService(firebaseService: FirebaseServiceImpl): FirebaseService
}