package com.sueta.main.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.directory.SearchManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideSearchManager(sdkContext: Context): SearchManager {
        return SearchManager.createSmartManager(sdkContext)
    }

    @Provides
    @Singleton
    fun provideSdkContext(): Context {
        return DGis.context()
    }

}