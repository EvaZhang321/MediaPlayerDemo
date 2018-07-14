package com.jingbanyun.mymediaplayer.util

import android.os.Handler
import android.os.Looper

/**
 * Created by Administrator on 2018/4/23.
 */
object TheadUtil {
    val handle = Handler(Looper.getMainLooper())
    /**
     *运行在主线程中
     */
    fun runOnMainThread(runnable: Runnable){
        handle.post(runnable)
    }
}