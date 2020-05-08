package com.coolweather.coolweatherjetpack.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dajiabao.common.util.ActivityManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        EventBus.getDefault().register(this)
        ActivityManager.getInstance().addActivity(this)
    }

    override fun onPause() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing()) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
        super.onPause()
    }


    override fun onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
        super.onDestroy()
        EventBus.getDefault().unregister(this);
        ActivityManager.getInstance().finishActivity(this);
    }

    private var mLoadingDialog: ProgressDialog? = null

    fun showLoadingDialog() {
        if (isDestroy()) {
            return
        }
        runOnUiThread {
            if (mLoadingDialog == null) {
                mLoadingDialog = ProgressDialog(this@BaseActivity)
            }

            mLoadingDialog!!.setMessage("")
            mLoadingDialog!!.show()
        }
    }

    private fun isDestroy(): Boolean {
        return this.isFinishing || this.isDestroyed
    }


    fun hideLoadingDialog() {
        if (isDestroy()) {
            return
        }
        runOnUiThread {
            if (mLoadingDialog != null) {
                mLoadingDialog!!.dismiss()
            }
        }
    }
}