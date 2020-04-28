package com.coolweather.coolweatherjetpack.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.ApplicationViewModel
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityLoginBinding
import com.coolweather.coolweatherjetpack.ui.FragmentContainerActivity
import com.coolweather.coolweatherjetpack.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

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

        observeData()
    }


    private fun observeData() {

        loginViewModel.result.observe(this, Observer {
            Log.e("+++", "jieguo :$it")
            startActivity(Intent(this,FragmentContainerActivity::class.java))
        })

        loginViewModel.showLoading.observe(this, Observer {
            if (it){
                showLoadingDialog()
            }else{
                hideLoadingDialog()
            }
        })
    }

    companion object{

        fun startActivity(phone:String,password:String,context:Activity){
            var intent = Intent()
            intent.setClass(context,LoginActivity::class.java)
            intent.putExtra("phone",phone)
            intent.putExtra("password",password)
            context.startActivity(intent)

        }

    }
}
