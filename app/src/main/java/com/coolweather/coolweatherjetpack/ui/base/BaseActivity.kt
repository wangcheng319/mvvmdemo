package com.coolweather.coolweatherjetpack.ui.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.coolweather.coolweatherjetpack.util.ProgressDialogFragment
import com.dajiabao.common.util.ActivityManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity:AppCompatActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        EventBus.getDefault().register(this)
        ActivityManager.getInstance().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
        ActivityManager.getInstance().finishActivity(this);
    }


    /**
     * 显示加载(转圈)对话框
     */
    fun showLoadingDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun hideLoadingDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }
}