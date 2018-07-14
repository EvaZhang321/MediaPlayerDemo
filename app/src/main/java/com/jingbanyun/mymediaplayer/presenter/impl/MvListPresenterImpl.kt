package com.jingbanyun.mymediaplayer.presenter.impl

import com.itheima.player.model.bean.MvPagerBean
import com.jingbanyun.mymediaplayer.base.BaseListPresenter
import com.jingbanyun.mymediaplayer.net.MvListRequest
import com.jingbanyun.mymediaplayer.net.ResponseHandler
import com.jingbanyun.mymediaplayer.presenter.interf.MvListPresenter
import com.jingbanyun.mymediaplayer.view.MvListView

/**
 * Created by Administrator on 2018/4/24.
 */
class MvListPresenterImpl(var code:String,var mvListView: MvListView?): MvListPresenter, ResponseHandler<MvPagerBean> {
    override fun onError(type: Int, msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: MvPagerBean) {
        if (type==BaseListPresenter.TYPE_INIT_OR_REFRESH) {
            mvListView?.loadSuccess(result)
        }else if(type==BaseListPresenter.TYPE_LOAD_MORE) {
            mvListView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        MvListRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH,code,0,this).excute()
    }

    override fun loadMore(offset: Int) {
        MvListRequest(BaseListPresenter.TYPE_LOAD_MORE,code,0,this).excute()
    }

    override fun destroyView() {
        if (mvListView!=null){
            mvListView=null
        }
    }
}