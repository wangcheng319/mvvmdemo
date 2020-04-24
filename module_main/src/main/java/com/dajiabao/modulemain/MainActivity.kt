package com.dajiabao.modulemain

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route


@Route(path = "/main_test/activity")
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.e("+++","hello this is main module")
        Toast.makeText(this,"hello this is main module",Toast.LENGTH_SHORT).show()
    }
}
