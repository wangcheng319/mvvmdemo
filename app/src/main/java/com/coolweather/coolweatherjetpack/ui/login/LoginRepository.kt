package com.coolweather.coolweatherjetpack.ui.login

import android.annotation.SuppressLint
import android.util.Log
import com.coolweather.coolweatherjetpack.data.network.BaseObserver
import com.coolweather.coolweatherjetpack.data.network.BaseRequestListener
import com.coolweather.coolweatherjetpack.data.network.BaseResponse
import com.coolweather.coolweatherjetpack.data.network.ServiceCreator
import com.coolweather.coolweatherjetpack.data.network.api.AccountService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun login(username: String, password: String, onResult: BaseRequestListener<String>){
        ServiceCreator.create(AccountService::class.java)
            .login(username,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<BaseResponse<String>>() {
                override fun onSuccess(response: BaseResponse<String>?) {
                    Log.e("+++","=====>${response?.errorCode}${response?.errorCode}${response?.errorMsg}")
                    onResult.onSuccess(response?.data)
                }

                override fun onFailing(response: BaseResponse<String>?) {
                    super.onFailing(response)
                    onResult.onFail(response?.errorMsg)
                }

            })
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