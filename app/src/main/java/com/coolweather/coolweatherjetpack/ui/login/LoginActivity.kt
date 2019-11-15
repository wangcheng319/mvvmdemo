package com.coolweather.coolweatherjetpack.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this

        loginViewModel = ViewModelProviders.of(this,LoginViewModelFactory(LoginRepository.getInstance())).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel

        button.setOnClickListener {
            progress_circular.visibility = View.VISIBLE
            loginViewModel.login(editText.text.toString().trim(),editText2.text.toString().trim())
        }

        observeData()
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
