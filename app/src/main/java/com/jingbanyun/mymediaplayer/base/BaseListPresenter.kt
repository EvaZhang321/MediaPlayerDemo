package com.jingbanyun.mymediaplayer.base

/**
 * Created by Administrator on 2018/4/23.
 * 所有下拉刷新和上拉加载更多界面的presenter的基类
 */
interface BaseListPresenter {
    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }
    fun loadDatas()

    fun loadMore(offset: Int)

    /**
     * 解绑view层和presenter
     */
    fun destroyView()
}