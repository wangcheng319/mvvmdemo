package com.coolweather.coolweatherjetpack.ui.weather

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.databinding.ActivityWeatherBinding
import com.coolweather.coolweatherjetpack.ui.base.BaseActivity
import com.coolweather.coolweatherjetpack.util.InjectorUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*

class WeatherActivity : BaseActivity() {

    val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory()).get(WeatherViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
        binding.viewModel = viewModel
        binding.resId = R.color.colorPrimary
        binding.lifecycleOwner = this
        viewModel.weatherId = if (viewModel.isWeatherCached()) {
            viewModel.getCachedWeather().basic.weatherId
        } else {
            intent.getStringExtra("weather_id")
        }
        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        observeData()
        viewModel.getWeather()
    }

    private fun observeData() {
        viewModel.isLoading.observe(this, Observer { isLoading->
            if (isLoading){
                showLoadingDialog()
                Toast.makeText(this,"=======",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"+++++++",Toast.LENGTH_LONG).show()
                hideLoadingDialog()
            }
         })
    }

}