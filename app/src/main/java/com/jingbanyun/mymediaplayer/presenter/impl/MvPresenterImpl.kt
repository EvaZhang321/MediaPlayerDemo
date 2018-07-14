package com.jingbanyun.mymediaplayer.presenter.impl

import com.itheima.player.model.bean.MvAreaBean
import com.jingbanyun.mymediaplayer.net.MvAreaRequest
import com.jingbanyun.mymediaplayer.net.ResponseHandler
import com.jingbanyun.mymediaplayer.presenter.interf.MvPresenter
import com.jingbanyun.mymediaplayer.view.MvView

/**
 * Created by Administrator on 2018/4/24.
 */
class MvPresenterImpl(var mvView :MvView): MvPresenter, ResponseHandler<List<MvAreaBean>> {
    override fun onError(type: Int, msg: String?) {
        mvView.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
        mvView.onSuccess(result)
    }

    /**
     * 加载区域数据
     */
    override fun loadDatas() {
        MvAreaRequest(this).excute()
    }
}