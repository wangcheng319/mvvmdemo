package com.coolweather.coolweatherjetpack.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import com.coolweather.coolweatherjetpack.ApplicationViewModel
import com.coolweather.coolweatherjetpack.CoolWeatherApplication
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.base.BaseActivity
import com.coolweather.coolweatherjetpack.ui.fragment.*
import kotlinx.android.synthetic.main.activity_fragment_container.*
import kotlin.system.exitProcess


class FragmentContainerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        initView()
        initData()
    }

    private var exitTime: Long = 0


    private fun initView() {
        initTopBar()

    }

    private fun initData() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root, MmkvTestFragment.newInstance("",""))
            .commitNow()
    }


    private fun initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("状态栏示例").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn:Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
//            exit()
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
