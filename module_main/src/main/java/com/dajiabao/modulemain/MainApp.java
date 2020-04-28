package com.dajiabao.modulemain;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dajiabao.common.BaseApp;

/**
 * @Author: wangc
 * @CreateDate: 2020/4/24 14:10
 */
public class MainApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

}
