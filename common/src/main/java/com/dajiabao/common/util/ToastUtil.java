package com.dajiabao.common.util;

import android.widget.Toast;

import com.dajiabao.common.BaseApp;

/**
 * Description: <吐司工具类><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class ToastUtil {

    public static void showToast(String message) {
        Toast.makeText(BaseApp.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resid) {
        Toast.makeText(BaseApp.getInstance(), BaseApp.getInstance().getString(resid), Toast.LENGTH_SHORT)
                .show();
    }
}