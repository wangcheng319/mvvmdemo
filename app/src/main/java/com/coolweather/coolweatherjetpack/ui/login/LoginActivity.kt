package com.coolweather.coolweatherjetpack.ui.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityLoginBinding
import com.coolweather.coolweatherjetpack.util.LogUtil
import com.coolweather.coolweatherjetpack.util.switchError
import com.coolweather.coolweatherjetpack.util.switchSuccess
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding:ActivityLoginBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        context = this

        loginViewModel = ViewModelProviders.of(this,LoginViewModelFactory(LoginRepository.getInstance())).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel

        button.setOnClickListener {
            login()
        }

        observeData()
    }

    private fun login() {
//        showLoadingDialog()
        loginViewModel.login(editText.text.toString().trim(),editText2.text.toString().trim())
            .observe(this, Observer {
            it.switchSuccess { rsp ->
//                hideLoadingDialog()
                rsp?.let {
                    if (rsp == null) {
                        Log.e("+++","登录失败")
                    } else {
                        Log.e("+++", "登录成功$rsp")
                    }
                }
            }
            it.switchError {
//                hideLoadingDialog()
            }
        })
    }

    private fun testConfig() {
//            if (BuildConfig.BUILD_TYPE.equals("debug")){
//                Log.e("+++","BUILD_TYPE_debug:"+BuildConfig.BUILD_TYPE)
//            }
//
//            if (BuildConfig.BUILD_TYPE.equals("release")){
//                Log.e("+++","BUILD_TYPE_release:"+BuildConfig.BUILD_TYPE)
//            }
//
//            if (BuildConfig.BUILD_TYPE.equals("pre")){
//                Log.e("+++","BUILD_TYPE_pre:"+BuildConfig.BUILD_TYPE)
//            }
        }


    private fun observeData() {
        loginViewModel.result.observe(this, Observer {
            if (TextUtils.isEmpty(it)){
                progress_circular.visibility = View.GONE
            }else{
                progress_circular.visibility = View.GONE
            }
        })
    }
}
