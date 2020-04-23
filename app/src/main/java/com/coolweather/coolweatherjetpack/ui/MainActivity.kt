package com.coolweather.coolweatherjetpack.ui

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.login.LoginActivity
import kotlin.system.exitProcess

@Route(path = "/test/activity", group = "test" )
class MainActivity : AppCompatActivity() {

    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    private fun  toLogin(){
        LoginActivity.startActivity("","",this)
    }

}
