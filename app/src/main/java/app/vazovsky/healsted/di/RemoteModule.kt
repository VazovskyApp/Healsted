package app.vazovsky.healsted.di

import app.vazovsky.healsted.core.service.RemoteHelper
import app.vazovsky.healsted.core.service.RemoteHelperImpl
import app.vazovsky.healsted.core.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://t.me/healsted/"

/** Модуль для ретрофита */
@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    @Named("notificationRetrofitService")
    fun provideApiService(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    @Named("notificationRemoteService")
    fun provideRemoteService(
        @Named("notificationRetrofitService") retrofit: Retrofit,
    ): RemoteService = retrofit.create(RemoteService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(
        remoteHelper: RemoteHelperImpl,
    ): RemoteHelper = remoteHelper
}