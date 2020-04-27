package com.dajiabao.common;

import android.app.Application;

/**
 * @Author: wangc
 * @CreateDate: 2020/4/24 14:09
 */
public abstract class BaseApp extends Application {
    private static BaseApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * Application 初始化
     */
    public abstract void initModuleApp(Application application);

    /**
     * 所有 Application 初始化后的自定义操作
     */
    public abstract void initModuleData(Application application);

    public static BaseApp getInstance(){
        return mApplication;
    }

}
