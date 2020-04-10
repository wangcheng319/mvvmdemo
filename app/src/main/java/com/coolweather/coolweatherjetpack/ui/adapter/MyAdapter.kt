package com.coolweather.coolweatherjetpack.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolweather.coolweatherjetpack.R

/**
 * @Author:         wangc
 * @CreateDate:     2020/4/10 14:46
 */
class MyAdapter(private val lists:MutableList<String>,private val mContext:Context):RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler,parent,false))
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = "this is $position"
    }

    /**
     * 如果派生类有一个主构造函数，其基类可以（并且必须） 用派生类主构造函数的参数就地初始化。
     * itemView必须传给父类
     */
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
         var nameTv:TextView = itemView.findViewById(R.id.textView)
    }
}