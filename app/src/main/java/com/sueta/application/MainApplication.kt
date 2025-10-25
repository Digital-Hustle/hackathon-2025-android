package com.sueta.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.directory.SearchManager

@HiltAndroidApp
class MainApplication : Application() {
    private var _sdkContext: Context? = null

    val sdkContext: Context
        get() = _sdkContext ?: throw IllegalStateException("DGis not initialized")

    override fun onCreate() {
        super.onCreate()

        _sdkContext = DGis.initialize(this)
    }
}

