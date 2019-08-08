package com.innovators.fantasyfootballfixtures.Controller

import Utilities.SharedPrefs
import android.app.Application
import android.content.Context

class App : Application() {


    companion object {
        lateinit var prefs: SharedPrefs
        lateinit var appContext:Context
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
        appContext = applicationContext
    }

}