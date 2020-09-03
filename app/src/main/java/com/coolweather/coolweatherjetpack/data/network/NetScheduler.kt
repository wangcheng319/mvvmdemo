package com.coolweather.coolweatherjetpack.data.network

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @ProjectName:    mvvmdemo
 * @Package:        com.coolweather.coolweatherjetpack.data.network
 * @ClassName:      NetScheduler
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/15 16:12
 * @Version:        1.0
 */
/**
 * 线程调度器
 */
object NetScheduler{
    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer {
                observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}