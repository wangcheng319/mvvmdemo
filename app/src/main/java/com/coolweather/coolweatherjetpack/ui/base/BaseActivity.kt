package com.coolweather.coolweatherjetpack.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

abstract class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        QMUIStatusBarHelper.translucent(this)
        initView()
        initData()
    }

    protected abstract val contentViewId: Int

    protected abstract fun initView()

    protected abstract fun initData()


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