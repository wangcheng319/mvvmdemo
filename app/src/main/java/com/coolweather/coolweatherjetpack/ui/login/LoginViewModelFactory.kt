package com.coolweather.coolweatherjetpack.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @ProjectName:    coolweatherjetpack
 * @Package:        com.coolweather.coolweatherjetpack.ui.login
 * @ClassName:      LoginViewModelFactory
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/14 16:40
 * @Version:        1.0
 */
class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = LoginViewModel(repository) as T
}
