package com.jingbanyun.mymediaplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.VideosBean
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.view.MvItemView

/**
 * Created by Administrator on 2018/4/24.
 * mv界面每一个list列表的适配器
 */
class MvListAdapter:BaseListAdapter<VideosBean,MvItemView>() {
    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }

    override fun refreshView(itemView: MvItemView, data: VideosBean) {
        itemView.setData(data)
    }

}