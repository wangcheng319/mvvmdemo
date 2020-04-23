package com.coolweather.coolweatherjetpack

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.coolweather.coolweatherjetpack.util.LogUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import java.util.*


class CoolWeatherApplication : Application() {

    private var mActivityLinkedList: LinkedList<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        sApp = this
        mActivityLinkedList = LinkedList()
        LogUtil.init()
        setActivityTitle()

        setNightMode()

        initARouter()

    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun setNightMode() {
        /**
         *  暗黑模式，跟随系统变化，另外资源文件需要设置两套，Activity 必须是 AppCompatActivity 实例
         * 在 drawable-xxhdpi 和 drawable-night-xxhdpi 目录下放置Light和Dark相同名字的图片，系统根据Light/Dark加载图片。
         */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun setActivityTitle() {
        sApp.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                mActivityLinkedList?.add(activity)
                QMUIStatusBarHelper.translucent(activity)
            }


            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                if (null!=mActivityLinkedList && mActivityLinkedList?.contains(activity)!!) {
                    mActivityLinkedList?.remove(activity);
                }
            }


        })
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