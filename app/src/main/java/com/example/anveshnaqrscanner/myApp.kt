package com.example.anveshnaqrscanner

import android.app.Application
import com.google.android.material.color.DynamicColors

class myApp: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}