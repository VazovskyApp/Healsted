package app.vazovsky.mypills.di

import app.vazovsky.mypills.data.repository.DashboardRepository
import app.vazovsky.mypills.data.repository.DashboardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDashboardRepository(dashboardRepository: DashboardRepositoryImpl): DashboardRepository
}