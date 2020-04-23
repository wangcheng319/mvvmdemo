package com.coolweather.coolweatherjetpack.ui

import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.base.BaseActivity
import com.coolweather.coolweatherjetpack.ui.fragment.ARouterFragment
import com.coolweather.coolweatherjetpack.ui.fragment.CameraXFragment
import com.coolweather.coolweatherjetpack.ui.fragment.ContactFragment
import com.coolweather.coolweatherjetpack.ui.fragment.TestFragment1
import com.coolweather.coolweatherjetpack.util.LogUtil
import kotlinx.android.synthetic.main.activity_fragment_container.*
import kotlin.system.exitProcess


class FragmentContainerActivity : BaseActivity() {

    private var exitTime: Long = 0

    override val contentViewId: Int
        get() = R.layout.activity_fragment_container

    override fun initView() {
        initTopBar()

    }

    override fun initData() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root, ARouterFragment.newInstance("",""))
            .commitNow()
    }


    private fun initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("沉浸式状态栏示例").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn:Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setOnClickListener { LogUtil.d("点击更多") }
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(
                applicationContext, "再按一次退出程序",
                Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            exitProcess(0)
        }
    }

}
