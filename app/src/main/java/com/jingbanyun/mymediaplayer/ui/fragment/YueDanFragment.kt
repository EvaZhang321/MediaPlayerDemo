package com.jingbanyun.mymediaplayer.ui.fragment

import com.itheima.player.model.bean.YueDanBean
import com.jingbanyun.mymediaplayer.adapter.YueDanAdapter
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.base.BaseListFragment
import com.jingbanyun.mymediaplayer.base.BaseListPresenter
import com.jingbanyun.mymediaplayer.presenter.impl.YueDanPresenterImpl
import com.jingbanyun.mymediaplayer.widget.YueDanItemView

/**
 * Created by Administrator on 2018/4/21.
 * 悦单界面
 */
class YueDanFragment: BaseListFragment<YueDanBean,YueDanBean.PlayListsBean, YueDanItemView>() {

    override fun getSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
        }

    override fun  getList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists
    }

}