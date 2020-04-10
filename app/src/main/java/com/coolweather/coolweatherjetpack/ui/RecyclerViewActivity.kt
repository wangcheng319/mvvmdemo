package com.coolweather.coolweatherjetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.adapter.MyAdapter

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        var rv:RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        val lists = mutableListOf("1","2","3","1","2","3","1","2","3","1","2","3")
        rv.adapter = MyAdapter(lists,this)
    }
}
