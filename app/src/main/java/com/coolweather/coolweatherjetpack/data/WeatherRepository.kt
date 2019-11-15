package com.coolweather.coolweatherjetpack.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolweather.coolweatherjetpack.data.db.WeatherDao
import com.coolweather.coolweatherjetpack.data.model.weather.HeWeather
import com.coolweather.coolweatherjetpack.data.model.weather.Weather
import com.coolweather.coolweatherjetpack.data.network.CoolWeatherNetwork
import com.coolweather.coolweatherjetpack.data.network.ServiceCreator
import com.coolweather.coolweatherjetpack.data.network.api.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository private constructor(private val weatherDao: WeatherDao, private val network: CoolWeatherNetwork) {

    suspend fun getWeather(weatherId: String): Weather {
        var weather = weatherDao.getCachedWeatherInfo()
        if (weather == null) weather = requestWeather(weatherId)
        return weather
    }

    fun refreshWeather(weatherId: String) = test(weatherId)

    suspend fun getBingPic(): String {
        var url = weatherDao.getCachedBingPic()
        if (url == null) url = requestBingPic()
        return url
    }

    suspend fun refreshBingPic() = requestBingPic()

    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()!!

    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = network.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        weatherDao.cacheWeatherInfo(weather)
        weather
    }

    private suspend fun requestBingPic() = withContext(Dispatchers.IO) {
        val url = network.fetchBingPic()
        weatherDao.cacheBingPic(url)
        url
    }

    fun test(weatherId: String): LiveData<Weather>? {
        Log.e("+++","=====>开始获取数据")
        val result = MutableLiveData<Weather>()
         ServiceCreator.create(WeatherService::class.java).getWeather(weatherId).enqueue(object:Callback<HeWeather>{

            override fun onResponse(call: Call<HeWeather>, response: Response<HeWeather>) {
                result.value = response.body()?.weather!![0]
                Log.e("+++","=====>获取数据成功")
            }

            override fun onFailure(call: Call<HeWeather>, t: Throwable) {

            }
        })
        return result
    }

    companion object {

        private lateinit var instance: WeatherRepository

        fun getInstance(weatherDao: WeatherDao, network: CoolWeatherNetwork): WeatherRepository {
            if (!::instance.isInitialized) {
                synchronized(WeatherRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = WeatherRepository(weatherDao, network)
                    }
                }
            }
            return instance
        }

    }

}