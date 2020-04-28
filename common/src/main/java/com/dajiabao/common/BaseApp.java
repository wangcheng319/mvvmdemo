package com.dajiabao.common;

import android.app.Application;

/**
 * @Author: wangc
 * @CreateDate: 2020/4/24 14:09
 */
public class BaseApp extends Application {
    private static BaseApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }


    public static BaseApp getInstance(){
        return mApplication;
    }

}
