package com.bonial.challengeapp

import android.app.Application
import com.bonial.challengeapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class ChallangeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChallangeApp)
            androidLogger()

            modules(appModule)
        }
    }
}