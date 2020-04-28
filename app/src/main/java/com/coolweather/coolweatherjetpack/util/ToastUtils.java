package com.coolweather.coolweatherjetpack.util;

import android.widget.Toast;

import com.coolweather.coolweatherjetpack.CoolWeatherApplication;
import com.dajiabao.common.BaseApp;

/**
 * @Author: wangc
 * @CreateDate: 2020/4/28 16:07
 */
public class ToastUtils {
    public static void showToast(String message) {
        Toast.makeText(CoolWeatherApplication.Companion.getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resid) {
        Toast.makeText(CoolWeatherApplication.Companion.getApplication(), CoolWeatherApplication.Companion.getApplication().getString(resid), Toast.LENGTH_SHORT)
                .show();
    }
}
