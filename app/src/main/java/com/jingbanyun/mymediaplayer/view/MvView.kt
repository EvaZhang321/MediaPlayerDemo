package com.jingbanyun.mymediaplayer.view

import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by Administrator on 2018/4/24.
 */
interface MvView {
    fun onError(msg: String?)
    fun onSuccess(result: List<MvAreaBean>)
}