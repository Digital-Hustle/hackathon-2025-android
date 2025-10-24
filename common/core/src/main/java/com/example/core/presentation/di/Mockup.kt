package com.example.core.presentation.di

import android.util.Log
import com.example.core.BuildConfig

interface Mockup

class RepositoryFactory<T>{
    fun <R : T, M : T> create(real: R, mock: M): T{
        return if(BuildConfig.USE_MOCKS) mock else real
    }
}
