package com.jingbanyun.mymediaplayer.presenter.impl

import com.itheima.player.model.bean.HomeItemBean
import com.jingbanyun.mymediaplayer.base.BaseListPresenter.Companion.TYPE_INIT_OR_REFRESH
import com.jingbanyun.mymediaplayer.base.BaseListPresenter.Companion.TYPE_LOAD_MORE
import com.jingbanyun.mymediaplayer.base.BaseView
import com.jingbanyun.mymediaplayer.net.HomeRequest
import com.jingbanyun.mymediaplayer.net.ResponseHandler
import com.jingbanyun.mymediaplayer.presenter.interf.HomePresenter

/**
 * Created by Administrator on 2018/4/23.
 */
class HomePresenterImpl(var homeView:BaseView<List<HomeItemBean>>?):HomePresenter, ResponseHandler<List<HomeItemBean>> {//构造方法的参数加上var/val可以在除了init方法外的方法中使用

    /**
     * 解绑view层和presenter
     */
    override fun destroyView(){
        if (homeView!=null) {
            homeView = null
        }
    }

    override fun onError(type:Int,msg: String?) {
        homeView?.onError(msg)
    }

    override fun onSuccess(type:Int,result: List<HomeItemBean>) {
        when(type){
            TYPE_INIT_OR_REFRESH->homeView?.loadSuccess(result)
            TYPE_LOAD_MORE->homeView?.loadMore(result)
        }
    }

    /**
     * 初始化数据或者刷新数据
     */
    override fun loadDatas() {
        //定义request
        HomeRequest(TYPE_INIT_OR_REFRESH,0,this).excute()
    }

    /**
     * 加载更多数据
     */
    override fun loadMore(offset:Int) {
        //定义request
        HomeRequest(TYPE_LOAD_MORE,offset, this).excute()
    }
}