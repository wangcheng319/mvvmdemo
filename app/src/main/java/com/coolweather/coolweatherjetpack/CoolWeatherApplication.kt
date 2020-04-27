package com.coolweather.coolweatherjetpack

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.coolweather.coolweatherjetpack.util.LogUtil
import com.dajiabao.common.AppConfig
import com.dajiabao.common.BaseApp
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.entry.ApplicationLike
import java.util.*


class CoolWeatherApplication : BaseApp() {

    private var mActivityLinkedList: LinkedList<Activity>? = null
    private var tinkerApplicationLike: ApplicationLike? = null


    override fun onCreate() {
        super.onCreate()

        context = this
        sApp = this
        mActivityLinkedList = LinkedList()
        LogUtil.init()
        setActivityTitle()

        setNightMode()

        initARouter()

        // 初始化组件 Application
//        initModuleApp(this);

        // 其他操作

        // 所有 Application 初始化后的操作
//        initModuleData(this);

        initTinker()

    }

    private fun initTinker() {
        Bugly.init(this, "f1ed221ce9", false);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)


        // 安装tinker
        Beta.installTinker()
    }

    override fun initModuleApp(application: Application?) {
        for ( moduleApp:String in AppConfig.moduleApps) {
            try {
                var clazz = Class.forName(moduleApp)
                var baseApp:BaseApp = clazz.newInstance() as BaseApp
                baseApp.initModuleApp(this)
            } catch (e:IllegalAccessException) {
                e.printStackTrace()
            } catch (e:InstantiationException) {
                e.printStackTrace()
            }
        }
    }

    override fun initModuleData(application: Application?) {
        for (moduleApp:String in AppConfig.moduleApps) {
            try {
                var clazz = Class.forName(moduleApp)
                var baseApp:BaseApp = clazz.newInstance() as BaseApp
                baseApp.initModuleData(this)
            } catch (e:ClassNotFoundException) {
                e.printStackTrace()
            } catch (e:IllegalAccessException) {
                e.printStackTrace()
            } catch (e:InstantiationException) {
                e.printStackTrace()
            }
        }
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.openDebug()
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
                    mActivityLinkedList?.remove(activity)
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