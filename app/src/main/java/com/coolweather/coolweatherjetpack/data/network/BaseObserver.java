package com.coolweather.coolweatherjetpack.data.network;

import android.text.TextUtils;
import android.util.Log;
import com.coolweather.coolweatherjetpack.util.toast.ToastUtils;
import com.google.gson.JsonParseException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {



    private static final String CONNECT_ERROR = "网络连接失败,请检查网络";
    private static final String CONNECT_TIMEOUT = "连接超时,请稍后再试";
    private static final String BAD_NETWORK = "服务器异常";
    private static final String PARSE_ERROR = "解析服务器响应数据失败";
    private static final String UNKNOWN_ERROR = "未知错误";
    private static final String RESPONSE_RETURN_ERROR = "服务器返回数据失败";

    public BaseObserver() {
    }



    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onNext(T response) {
//        Log.e("+++","onNext"+response);
        if (response.getErrorCode() == -1){
            onFailing(response.getErrorMsg());
        }else{
            onSuccess(response);
        }
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof retrofit2.HttpException) {
            //HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            //连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            //其他错误
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(CONNECT_ERROR);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(CONNECT_TIMEOUT);
                break;

            case BAD_NETWORK:
                ToastUtils.show(BAD_NETWORK);
                break;

            case PARSE_ERROR:
                ToastUtils.show(PARSE_ERROR);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(UNKNOWN_ERROR);
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 网络请求成功并返回正确值
     *
     * @param response 返回值
     */
    public abstract void onSuccess(T response);

    /**
     * 网络请求成功但是返回值是错误的
     *
     * @param response 返回值
     */
    public void onFailing(String response) {
        if (TextUtils.isEmpty(response)) {
            ToastUtils.show(RESPONSE_RETURN_ERROR);
        } else {
            ToastUtils.show(response);
        }
    }


    /**
     * 网络请求失败原因
     */
    public enum ExceptionReason {
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
}
