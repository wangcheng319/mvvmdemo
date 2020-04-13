package com.coolweather.coolweatherjetpack.data.network.api

import com.coolweather.coolweatherjetpack.data.model.account.LoginRsp
import com.coolweather.coolweatherjetpack.data.network.BaseResponse
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @ProjectName:    coolweatherjetpack
 * @Package:        com.coolweather.coolweatherjetpack.data.network.api
 * @ClassName:      AccountService
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/14 16:51
 * @Version:        1.0
 */
interface AccountService {

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username")username:String, @Field("password")password:String) : Observable<BaseResponse<LoginRsp>>

}