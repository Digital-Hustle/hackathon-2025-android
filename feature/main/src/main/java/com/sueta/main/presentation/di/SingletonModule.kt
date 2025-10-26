package com.sueta.main.presentation.di

import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.di.RepositoryFactory
import com.sueta.main.data.network.MainApiService
import com.sueta.main.data.repository.MainMockRepository
import com.sueta.main.data.repository.MainRepositoryImpl
import com.sueta.main.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.directory.SearchManager
import ru.dgis.sdk.map.RouteEditorSource
import ru.dgis.sdk.routing.RouteEditor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideSearchManager(sdkContext: Context): SearchManager =
        SearchManager.createSmartManager(sdkContext)


    @Provides
    @Singleton
    fun provideSdkContext(): Context = DGis.context()





    @Singleton
    @Provides
    fun provideMainAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): MainApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(MainApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: MainApiService,
        userStorage: UserStorage
    ): MainRepository = RepositoryFactory<MainRepository>().create(
        MainRepositoryImpl(apiService, userStorage), MainMockRepository(userStorage)
    )

}