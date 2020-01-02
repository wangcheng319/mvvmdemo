package com.coolweather.coolweatherjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal

class CoolWeatherApplication : Application() {
    var str = "sourcetree+feature test"


    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context = this
        sApp = this
    }



    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var sApp: Application

        fun getApplication(): Application {
            return sApp
        }
    }

}