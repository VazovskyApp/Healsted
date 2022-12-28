package app.vazovsky.mypills.di

import app.vazovsky.mypills.core.repository.NotificationRepository
import app.vazovsky.mypills.core.repository.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindNotificationRepository(notificationRepository: NotificationRepositoryImpl): NotificationRepository
}