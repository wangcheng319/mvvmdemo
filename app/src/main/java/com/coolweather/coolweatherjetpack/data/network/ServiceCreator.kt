package com.coolweather.coolweatherjetpack.data.network

import com.coolweather.coolweatherjetpack.BuildConfig
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ServiceCreator {

//    private const val BASE_URL = "https://www.wanandroid.com/"
    private const val BASE_URL = BuildConfig.BASE_URL

    private val httpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
        .eventListener(object : EventListener(){
            override fun callStart(call: Call) {
                super.callStart(call)
            }

            override fun callEnd(call: Call) {
                super.callEnd(call)
            }

            override fun callFailed(call: Call, ioe: IOException) {
                super.callFailed(call, ioe)
            }

        })
        .connectTimeout(5*1000,TimeUnit.MILLISECONDS)
        .followRedirects(true)
        .retryOnConnectionFailure(true)


    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())



    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}