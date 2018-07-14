package com.jingbanyun.mymediaplayer.ui.fragment

import com.itheima.player.model.bean.HomeItemBean
import com.jingbanyun.mymediaplayer.adapter.HomeAdapter
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.base.BaseListFragment
import com.jingbanyun.mymediaplayer.base.BaseListPresenter
import com.jingbanyun.mymediaplayer.presenter.impl.HomePresenterImpl
import com.jingbanyun.mymediaplayer.widget.HomeItemView

/**
 * Created by Administrator on 2018/4/21.
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {
    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }
}