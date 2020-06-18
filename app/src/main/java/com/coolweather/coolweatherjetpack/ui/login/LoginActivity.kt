package com.coolweather.coolweatherjetpack.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityLoginBinding
import com.coolweather.coolweatherjetpack.ui.FragmentContainerActivity
import com.coolweather.coolweatherjetpack.ui.base.BaseActivity
import com.dajiabao.common.view.CommonDialogFragment
import com.dajiabao.common.view.RotateProgressDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.progressDialog

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding:ActivityLoginBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        context = this
//        loginViewModel = ViewModelProviders.of(this,LoginViewModelFactory(LoginRepository.getInstance())).get(LoginViewModel::class.java)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = loginViewModel

        observeData()

        //禁止屏幕截屏
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }


    private fun observeData() {

        loginViewModel.result.observe(this, Observer {
            Log.e("+++", "jieguo :$it")
            startActivity(Intent(this,FragmentContainerActivity::class.java))
        })

        loginViewModel.showLoading.observe(this, Observer {
            if (it){
                showLoadingDialog()
//                loading.loading(true)
//                loading.visibility = View.VISIBLE
            }else{
                hideLoadingDialog()
//                loading.loading(false)
//                loading.visibility = View.GONE
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
