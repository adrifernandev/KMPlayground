package com.adrifernandevs.kmplayground

import android.app.Application
import com.adrifernandevs.kmplayground.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class KMPlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KMPlaygroundApplication)
        }
    }
}