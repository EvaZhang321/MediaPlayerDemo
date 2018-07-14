package com.jingbanyun.mymediaplayer.presenter.impl

import com.itheima.player.model.bean.YueDanBean
import com.jingbanyun.mymediaplayer.base.BaseListPresenter.Companion.TYPE_INIT_OR_REFRESH
import com.jingbanyun.mymediaplayer.base.BaseListPresenter.Companion.TYPE_LOAD_MORE
import com.jingbanyun.mymediaplayer.base.BaseView
import com.jingbanyun.mymediaplayer.net.ResponseHandler
import com.jingbanyun.mymediaplayer.net.YueDanRequest
import com.jingbanyun.mymediaplayer.presenter.interf.YueDanPresenter

/**
 * Created by Administrator on 2018/4/23.
 */
class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResponseHandler<YueDanBean> {

    /**
     * 解绑view层和presenter
     */
    override fun destroyView() {
        if (yueDanView != null) {
            yueDanView = null
        }
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        when (type) {
            TYPE_INIT_OR_REFRESH -> yueDanView?.loadSuccess(result)
            TYPE_LOAD_MORE -> yueDanView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        YueDanRequest(TYPE_INIT_OR_REFRESH, 0, this).excute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(TYPE_LOAD_MORE, offset, this).excute()
    }
}