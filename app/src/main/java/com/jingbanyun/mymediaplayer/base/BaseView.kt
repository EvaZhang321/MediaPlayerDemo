package com.jingbanyun.mymediaplayer.base

/**
 * Created by Administrator on 2018/4/23.
 * 所有下拉刷新和上拉加载更多列表界面的view的基类
 */
 interface BaseView<RESPONSE> {
    /**
     * 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 初始化数据成功
     */
    fun loadSuccess(response: RESPONSE?)

    /**
     * 加载更多数据成功
     */
    fun loadMore(response: RESPONSE?)
}