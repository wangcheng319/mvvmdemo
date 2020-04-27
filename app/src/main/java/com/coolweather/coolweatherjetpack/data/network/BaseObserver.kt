package com.coolweather.coolweatherjetpack.data.network

import io.reactivex.disposables.Disposable
import com.google.gson.JsonParseException
import org.json.JSONException
import com.coolweather.coolweatherjetpack.util.toast.ToastUtils
import io.reactivex.Observer
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

abstract class BaseObserver<T : BaseResponse<*>?> : Observer<T> {
    override fun onSubscribe(d: Disposable) {}
    override fun onNext(response: T) {
        if (response!!.errorCode == -1) {
            onFailing(response.errorMsg)
        } else {
            onSuccess(response)
        }
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) { //HTTP错误
            onException(ExceptionReason.BAD_NETWORK)
        } else if (e is ConnectException || e is UnknownHostException) { //连接错误
            onException(ExceptionReason.CONNECT_ERROR)
        } else if (e is InterruptedIOException) { //连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT)
        } else if (e is JsonParseException || e is JSONException || e is ParseException) { //解析错误
            onException(ExceptionReason.PARSE_ERROR)
        } else { //其他错误
            onException(ExceptionReason.UNKNOWN_ERROR)
        }
    }

    private fun onException(reason: ExceptionReason) {
        when (reason) {
            ExceptionReason.CONNECT_ERROR -> ToastUtils.show(CONNECT_ERROR)
            ExceptionReason.CONNECT_TIMEOUT -> ToastUtils.show(CONNECT_TIMEOUT)
            ExceptionReason.BAD_NETWORK -> ToastUtils.show(BAD_NETWORK)
            ExceptionReason.PARSE_ERROR -> ToastUtils.show(PARSE_ERROR)
            ExceptionReason.UNKNOWN_ERROR -> ToastUtils.show(UNKNOWN_ERROR)
        }
    }

    override fun onComplete() {}
    /**
     * 网络请求成功并返回正确值
     *
     * @param response 返回值
     */
    abstract fun onSuccess(response: T)

    /**
     * 网络请求成功但是返回值是错误的
     *
     * @param response 返回值
     */
    open fun onFailing(response: String?) {
        if (android.text.TextUtils.isEmpty(response)) {
            ToastUtils.show(RESPONSE_RETURN_ERROR)
        } else {
            ToastUtils.show(response)
        }
    }

    /**
     * 网络请求失败原因
     */
    enum class ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }

    companion object {
        private const val CONNECT_ERROR = "网络连接失败,请检查网络"
        private const val CONNECT_TIMEOUT = "连接超时,请稍后再试"
        private const val BAD_NETWORK = "服务器异常"
        private const val PARSE_ERROR = "解析服务器响应数据失败"
        private const val UNKNOWN_ERROR = "未知错误"
        private const val RESPONSE_RETURN_ERROR = "服务器返回数据失败"
    }
}