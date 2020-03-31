package com.coolweather.coolweatherjetpack.ui.login

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.BuildConfig
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityLoginBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        var root:ViewGroup = window.decorView as ViewGroup
        var btn = Button(this)
        button.setOnClickListener {

            loginViewModel.login(editText.text.toString().trim(),editText2.text.toString().trim())
        }

        observeData()

        button2.setOnClickListener {
            var bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.layout_dialog)
            bottomSheetDialog.show()
        }

//        button3.setOnClickListener { root.removeView(btn) }
        button3.setOnClickListener { testConfig() }
    }

    private fun testConfig() {
            if (BuildConfig.BUILD_TYPE.equals("debug")){
                Log.e("+++","BUILD_TYPE_debug:"+BuildConfig.BUILD_TYPE)
            }

            if (BuildConfig.BUILD_TYPE.equals("release")){
                Log.e("+++","BUILD_TYPE_release:"+BuildConfig.BUILD_TYPE)
            }

            if (BuildConfig.BUILD_TYPE.equals("pre")){
                Log.e("+++","BUILD_TYPE_pre:"+BuildConfig.BUILD_TYPE)
            }
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
