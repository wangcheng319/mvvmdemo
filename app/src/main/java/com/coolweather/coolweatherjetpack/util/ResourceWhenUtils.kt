package com.coolweather.coolweatherjetpack.util

import com.coolweather.coolweatherjetpack.data.Resource


/**
 *
 * 网络请求返回处理
 * @author YiXin on 2019-08-13.
 */


fun <T> Resource<T>.switch(
    success: (T?) -> Unit,
    error: (message: String?) -> Unit,
    loading: () -> Unit
) {
    when (this.status) {
        Status.SUCCESS -> success(this.data)
        Status.ERROR -> error(this.message)
        Status.LOADING -> loading()
    }
}

infix fun <T> Resource<T>.switchLoading(loading: () -> Unit) {
    if (this.status == Status.LOADING)
        return loading()
}

infix fun <T> Resource<T>.switchSuccess(success: (T?) -> Unit) {
    if (this.status == Status.SUCCESS)
        return success(this.data)
}

infix fun <T> Resource<T>.switchError(error: (message: String?) -> Unit) {
    if (this.status == Status.ERROR)
        return error(this.message)
}

infix fun <T> Resource<T>.switchDataError(error: (message: String?, data: T?) -> Unit) {
    if (this.status == Status.ERROR)
        return error(this.message, this.data)
}
