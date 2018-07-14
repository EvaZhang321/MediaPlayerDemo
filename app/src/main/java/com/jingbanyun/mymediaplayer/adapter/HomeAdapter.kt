package com.jingbanyun.mymediaplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.HomeItemBean
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.widget.HomeItemView

/**
 * Created by Administrator on 2018/4/21.
 */
class HomeAdapter : BaseListAdapter<HomeItemBean,HomeItemView>() {
    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }

    override fun refreshView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
        //just a test
    }
}