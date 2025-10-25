package com.sueta.core.presentation.di

import com.sueta.core.BuildConfig

interface Mockup

class RepositoryFactory<T>{
    fun <R : T, M : T> create(real: R, mock: M): T{
        return if(BuildConfig.USE_MOCKS) mock else real
    }
}
