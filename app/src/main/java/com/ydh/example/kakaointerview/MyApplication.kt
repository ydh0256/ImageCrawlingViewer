package com.ydh.example.kakaointerview

import android.app.Application
import com.ydh.example.kakaointerview.di.appModules
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModules) }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}