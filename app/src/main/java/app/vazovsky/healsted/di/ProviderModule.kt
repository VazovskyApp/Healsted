package app.vazovsky.healsted.di

import app.vazovsky.healsted.data.appinfo.repository.BuildConfigProvider
import app.vazovsky.healsted.data.appinfo.repository.BuildConfigProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Модуль для провайдеров */
@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    abstract fun bindBuildConfigProvider(
        buildConfigProvider: BuildConfigProviderImpl
    ): BuildConfigProvider
}