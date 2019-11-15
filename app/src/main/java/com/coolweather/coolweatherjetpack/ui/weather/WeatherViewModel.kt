package com.coolweather.coolweatherjetpack.ui.weather

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolweather.coolweatherjetpack.CoolWeatherApplication
import com.coolweather.coolweatherjetpack.data.WeatherRepository
import com.coolweather.coolweatherjetpack.data.model.weather.Weather
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var weather = MutableLiveData<Weather>()

    var bingPicUrl = MutableLiveData<String>()

    var refreshing = MutableLiveData<Boolean>()

    var weatherInitialized = MutableLiveData<Boolean>()

    var weatherId = ""

    var isLoading = MutableLiveData<Boolean>()

    fun getWeather():LiveData<Weather> {
//        launch ({
//            weather.value = repository.getWeather(weatherId)
//            weatherInitialized.value = true
//        }, {
//            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
//        })
//        getBingPic(false)

//        weather.value = repository.test(weatherId)
        weatherInitialized.value = true

        return weather

    }

    fun showDialog(){
        isLoading.value = true
    }

    fun refreshWeather(): LiveData<Weather>? {
//        launch ({
//            weather.value = repository.refreshWeather(weatherId)
//            refreshing.value = false
//            isLoading.value = false
//            weatherInitialized.value = true
//        }, {
//            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
//            refreshing.value = false
//        })

//        getBingPic(true)
        Log.e("+++", "refreshWeather1:$weather")
        return repository.refreshWeather(weatherId)
    }

    fun isWeatherCached() = repository.isWeatherCached()

    fun getCachedWeather() = repository.getCachedWeather()

    fun onRefresh() {
        refreshWeather()
    }

    private fun getBingPic(refresh: Boolean) {
        launch({
            bingPicUrl.value = if (refresh) repository.refreshBingPic() else repository.getBingPic()
        }, {
            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}