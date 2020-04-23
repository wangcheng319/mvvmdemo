package com.dajiabao.modulemain

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter


@Route(path = "/test/activity", group = "test" )
class MainActivity : AppCompatActivity() {

    @Autowired
    var name: String? = null
    @Autowired
    var age = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ARouter.getInstance().inject(this)

        Log.e("+++", "name:$name,age:$age")

        var textView = findViewById<TextView>(R.id.textView)

        var str = "name:$name,age:$age"
        textView.text = str

    }
}
