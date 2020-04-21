package com.coolweather.coolweatherjetpack.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

open class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
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

    protected fun isDestroy(): Boolean {
        return this.isFinishing || this.isDestroyed
    }

    fun showLoadingDialog(text: String) {
        if (isDestroy()) {
            return
        }
        runOnUiThread {
            if (mLoadingDialog == null) {
                mLoadingDialog = ProgressDialog(this@BaseActivity)
            }

            mLoadingDialog!!.setMessage(text)
            mLoadingDialog!!.show()
        }
    }

    protected fun showLoadingDialog(imgRes: Int, text: String, load: String) {
        if (isDestroy()) {
            return
        }
        runOnUiThread {
            if (mLoadingDialog == null) {
                mLoadingDialog = ProgressDialog(this@BaseActivity)
            }

//            mLoadingDialog!!.setMessage(imgRes, text, load)
            mLoadingDialog!!.show()
        }
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