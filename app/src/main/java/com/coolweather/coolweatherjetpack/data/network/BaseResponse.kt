package com.coolweather.coolweatherjetpack.data.network

import java.io.Serializable

/**
 *
 * @ProjectName:    coolweatherjetpack
 * @Package:        com.coolweather.coolweatherjetpack.data.network
 * @ClassName:      BaseResponse
 * @Description:     java类作用描述
 * @Author:         wangc
 * @CreateDate:     2019/11/15 10:43
 * @Version:        1.0
 */
open class BaseResponse<T> :Serializable{
    val errorCode: Int? = null
    var data: T? = null
    var errorMsg: String? = null
}