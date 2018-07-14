package com.jingbanyun.mymediaplayer.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

/**
 * Created by Administrator on 2018/4/23.
 * 所有请求的基类
 */
open class MRequest<RESPONSE>(val type:Int,val url:String,val handler:ResponseHandler<RESPONSE>){
    /**
     * 解析网络请求的结果
     */
    fun parseResult(result: String?): RESPONSE {
        val gson = Gson()
        //获取范型参数的具体类型
        val type = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        //获取范型类型
        return gson.fromJson<RESPONSE>(result, type)
    }

    /**
     * 发送网络请求
     */
    fun excute(){
        NetManager.manager.sendRequest(this)
    }
}