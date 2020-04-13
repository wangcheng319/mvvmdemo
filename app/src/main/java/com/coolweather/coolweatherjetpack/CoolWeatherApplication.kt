package com.coolweather.coolweatherjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.coolweather.coolweatherjetpack.util.LogUtil

class CoolWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        sApp = this
        LogUtil.init()
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