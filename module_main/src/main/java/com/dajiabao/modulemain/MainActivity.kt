package com.dajiabao.modulemain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.main_activity_main.*


@Route(path = "/main_test/activity")
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)

        Log.e("+++","hello this is main module")
        Toast.makeText(this,"hello this is main module",Toast.LENGTH_SHORT).show()

        main_button.setOnClickListener { toAccountModule() }
        initTopBar()

    }


    private fun initTopBar() {
        QMUIStatusBarHelper.translucent(this)
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("MainModule_Main").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn:Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }


    private fun toAccountModule() {
        ARouter.getInstance().build("/account_test/activity").navigation()
    }
}
