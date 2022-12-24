package app.vazovsky.mypills.di

import app.vazovsky.mypills.data.repository.DashboardRepository
import app.vazovsky.mypills.data.repository.DashboardRepositoryImpl
import app.vazovsky.mypills.data.repository.HealthRepository
import app.vazovsky.mypills.data.repository.HealthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDashboardRepository(dashboardRepository: DashboardRepositoryImpl): DashboardRepository

    @Binds
    abstract fun bindHealthRepository(healthRepository: HealthRepositoryImpl): HealthRepository
}