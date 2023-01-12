package app.vazovsky.healsted.di

import app.vazovsky.healsted.core.repository.NotificationRepository
import app.vazovsky.healsted.core.repository.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Модуль для уведомлений */
@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepository: NotificationRepositoryImpl,
    ): NotificationRepository
}