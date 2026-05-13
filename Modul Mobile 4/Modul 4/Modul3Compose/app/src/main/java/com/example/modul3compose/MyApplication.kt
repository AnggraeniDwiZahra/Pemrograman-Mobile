package com.example.modul3compose

import android.app.Application
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            Timber.plant(Timber.DebugTree())
        }
    }
}