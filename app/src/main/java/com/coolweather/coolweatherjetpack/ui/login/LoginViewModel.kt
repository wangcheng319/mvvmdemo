package com.coolweather.coolweatherjetpack.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coolweather.coolweatherjetpack.data.model.account.LoginRsp
import com.coolweather.coolweatherjetpack.data.network.BaseObserver
import com.coolweather.coolweatherjetpack.util.ToastUtils
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import java.lang.Exception

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

    var result = MutableLiveData<LoginRsp>()
    var showLoading = MutableLiveData<Boolean>(false)
    var userName = MutableLiveData<String>()
    var passWord = MutableLiveData<String>()

    init {
    }


    fun login() {
        if (TextUtils.isEmpty(userName.value) || TextUtils.isEmpty(passWord.value)){
            ToastUtils.showToast("用户名或密码不能为空")
            return
        }
        showLoading.value = true
        loginRepository.login(userName.value!!, passWord.value!!,object : BaseObserver<LoginRsp>() {

            override fun onFailing(response: String?) {
                super.onFailing(response)
                showLoading.value = false
            }

            override fun onSuccess(response: LoginRsp?) {
                showLoading.value = false
                result.value = response
            }
        })
    }

    /**
     * 获取glide加载进度条
     */
    fun getProgress(url:String){
        ProgressManager.getInstance().addResponseListener(url,object :ProgressListener{
            override fun onProgress(progressInfo: ProgressInfo?) {
            }

            override fun onError(id: Long, e: Exception?) {

            }

        })
    }

}