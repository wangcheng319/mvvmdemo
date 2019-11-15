package com.coolweather.coolweatherjetpack.data.network;


import com.coolweather.coolweatherjetpack.util.toast.ToastUtils;

public abstract class BaseRequestListener<T> {

    public abstract void onSuccess(T response);

    public void onFail(String message) {
        ToastUtils.show(message);
    }

}
