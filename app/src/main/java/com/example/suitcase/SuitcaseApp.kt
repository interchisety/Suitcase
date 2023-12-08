package com.example.suitcase

import android.app.Application

class SuitcaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.provide(this)
    }
}