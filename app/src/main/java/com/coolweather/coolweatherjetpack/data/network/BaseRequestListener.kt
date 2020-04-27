package com.coolweather.coolweatherjetpack.data.network

import com.coolweather.coolweatherjetpack.util.toast.ToastUtils

abstract class BaseRequestListener<T> {

    abstract fun onSuccess(response: T)

    fun onFail(message: String?) {
        ToastUtils.show(message)
    }
}