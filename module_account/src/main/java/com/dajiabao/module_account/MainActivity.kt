package com.dajiabao.module_account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.account_activity_main.*

@Route(path = "/account_test/activity")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity_main)

        Log.e("+++","hello this is account module")
        Toast.makeText(this,"hello this is account module", Toast.LENGTH_SHORT).show()

        initTopBar()
    }


    private fun initTopBar() {
        QMUIStatusBarHelper.translucent(this)
        topbar.addLeftBackImageButton().setOnClickListener { finish() }

        topbar.setTitle("AccountModule_Main").setTextColor(resources.getColor(R.color.qmui_config_color_white))

        var rightBtn: Button = topbar.addRightTextButton("更多",R.id.right)
        rightBtn.setTextColor(resources.getColor(R.color.qmui_config_color_white))
    }
}
