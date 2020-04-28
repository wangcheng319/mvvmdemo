package com.coolweather.coolweatherjetpack.ui.login

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolweather.coolweatherjetpack.CoolWeatherApplication
import com.coolweather.coolweatherjetpack.data.Resource
import com.coolweather.coolweatherjetpack.data.model.account.LoginRsp
import com.coolweather.coolweatherjetpack.data.network.BaseObserver
import com.coolweather.coolweatherjetpack.data.network.BaseResponse
import com.coolweather.coolweatherjetpack.data.network.NetScheduler
import com.coolweather.coolweatherjetpack.data.network.ServiceCreator
import com.coolweather.coolweatherjetpack.data.network.api.AccountService
import com.dajiabao.common.mvvm.model.BaseModel

/**
 *
 * @ProjectName:    coolweatherjetpack
 * @Package:        com.coolweather.coolweatherjetpack.ui.login
 * @ClassName:      LoginRepository
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/14 16:40
 * @Version:        1.0
 */
class LoginRepository {

    fun login(username: String, password: String,baseObserver: BaseObserver<BaseResponse<LoginRsp>>) {

        return ServiceCreator.create(AccountService::class.java)
                .login(username,password)
                .compose(NetScheduler.compose())
                .subscribe(baseObserver)
    }

    companion object {

        private lateinit var instance: LoginRepository

        fun getInstance(): LoginRepository {
            if (!::instance.isInitialized) {
                synchronized(LoginRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = LoginRepository()
                    }
                }
            }
            return instance
        }

    }
}