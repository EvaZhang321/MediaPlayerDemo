package com.jingbanyun.mymediaplayer.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jingbanyun.mymediaplayer.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by Administrator on 2018/4/23.
 * 具有所有下拉刷新和上拉加载更多列表界面的基类
 * 基类抽取
 * HomeView->BaseView
 * Adapter->BaseAdapter
 * Presenter->BasePresenter
 */
abstract class BaseListFragment<RESPONSE,ITEMBEAN,ITEMVIEW:View> : BaseFragment(), BaseView<RESPONSE> {
    override fun onError(message: String?) {
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
        myToast("加载数据失败")
    }

    override fun loadSuccess(response: RESPONSE?) {
        //刷新列表
        adapter.updateList(getList(response))
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
    }

    override fun loadMore(response: RESPONSE?) {
        //刷新列表
        adapter.loadMore(getList(response))
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
    }

    val adapter by lazy { getSpecialAdapter() }

    val presenter: BaseListPresenter by lazy { getSpecialPresenter() }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_list, null)
    }

    override fun initListener() {
        //初始化recycleview
        recycleView.layoutManager = LinearLayoutManager(context)
        //适配
        recycleView.adapter = adapter

        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            //刷新监听
            presenter.loadDatas()
        }

        //监听列表滑动
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //是否最后一条已经显示
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == adapter.itemCount - 1) {
                            //最后一条已经显示 加载更多
                            presenter.loadMore(adapter.itemCount - 1)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        //初始化数据
        presenter.loadDatas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //解绑presenter
        presenter.destroyView()
    }

    /**
     * 获取适配器adapter
     */
    abstract  fun getSpecialAdapter():BaseListAdapter<ITEMBEAN,ITEMVIEW>

    /**
     * 获取Presenter
     */
    abstract fun getSpecialPresenter(): BaseListPresenter

    /**
     * 从返回结果中获取list集合
     */
    abstract fun  getList(response: RESPONSE?): List<ITEMBEAN>?
}