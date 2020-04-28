package com.coolweather.coolweatherjetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.util.LogUtils
import kotlinx.android.synthetic.main.activity_fragment_container.*

class TransformActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTopBar()
    }


    private fun initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("共享元素跳转").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn: Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setOnClickListener { LogUtils.d("点击更多") }
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }

}
