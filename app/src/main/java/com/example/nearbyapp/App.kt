package com.example.nearbyapp

import android.app.Application
import com.nearby.app.di.dataModule
import com.example.nearbyapp.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(dataModule, mainModule))
        }

    }
}