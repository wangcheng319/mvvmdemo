package com.coolweather.coolweatherjetpack.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coolweather.coolweatherjetpack.R

/**
 * @Author:         wangc
 * @CreateDate:     2020/5/9 14:51
 */
class TestAdapter(private val mDatas: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler,mDatas) {

    override fun convert(holder: BaseViewHolder, item: String) {

        holder.setText(R.id.textView,"第($item)项${mDatas.size}")
    }
}