package com.coolweather.coolweatherjetpack.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coolweather.coolweatherjetpack.data.network.BaseRequestListener

/**
 *
 * @ProjectName:    coolweatherjetpack
 * @Package:        com.coolweath etpack.ui.login
 * @ClassName:      LoginViewModel
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/14 16:39
 * @Version:        1.0
 */
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    var result = MutableLiveData<String>()

    fun login(username: String, password: String) {
        loginRepository.login(username,password,object : BaseRequestListener<String>() {

            override fun onSuccess(response: String?) {
                Log.e("+++","结果：$response")
                result.value = response
            }

            override fun onFail( message: String?) {
                super.onFail(message)
                result.value = null
            }

        })
    }

}