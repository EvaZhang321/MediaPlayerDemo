package com.jingbanyun.mymediaplayer.ui.fragment

import android.view.View
import com.itheima.player.model.bean.MvAreaBean
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.adapter.MvPagerAdapter
import com.jingbanyun.mymediaplayer.base.BaseFragment
import com.jingbanyun.mymediaplayer.presenter.impl.MvPresenterImpl
import com.jingbanyun.mymediaplayer.presenter.interf.MvPresenter
import com.jingbanyun.mymediaplayer.view.MvView
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 * Created by Administrator on 2018/4/21.
 */
class MvFragment :BaseFragment(), MvView {
    override fun onError(msg: String?) {
        myToast("加载区域数据失败")
    }

    override fun onSuccess(result: List<MvAreaBean>) {
        //在fragment中管理fragment,需要用childFragmentManager
       val adapter = MvPagerAdapter(context,result,childFragmentManager)
        viewpager.adapter = adapter
        tabLayout.setupWithViewPager(viewpager)
    }

    val presenter:MvPresenter by lazy { MvPresenterImpl(this) }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_mv,null)
    }

    override fun initListener() {

    }

    override fun initData() {
        //加载区域数据
        presenter.loadDatas()
    }
}