package com.coolweather.coolweatherjetpack.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.fragment.TestFragment1
import com.coolweather.coolweatherjetpack.util.LogUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_fragment_container.*


class FragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fragment_container)

        initTopBar()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.root, TestFragment1.newInstance("",""))
            .commitNow()
    }

    private fun initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("沉浸式状态栏示例").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn:Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setOnClickListener { LogUtil.d("点击更多") }
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }

}
