package com.jingbanyun.mymediaplayer.net

/**
 * Created by Administrator on 2018/4/23.
 * 接口请求的回调
 */
interface ResponseHandler<RESPONSE>  {
    fun onError( type:Int,msg:String?)
    //成功返回的数据类型不确定，使用范型来接收
    fun onSuccess( type:Int,result:RESPONSE)
}