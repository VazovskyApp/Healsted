package app.vazovsky.mypills.di

import app.vazovsky.mypills.core.service.RemoteHelper
import app.vazovsky.mypills.core.service.RemoteHelperImpl
import app.vazovsky.mypills.core.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    @Named("notificationRetrofitService")
    fun provideApiService(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://automation.basalam.com")
        .build()

    @Singleton
    @Provides
    @Named("notificationRemoteService")
    fun provideRemoteService(@Named("notificationRetrofitService") retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiHelper(remoteHelperImpl: RemoteHelperImpl): RemoteHelper = remoteHelperImpl
}