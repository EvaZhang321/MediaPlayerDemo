package com.jingbanyun.mymediaplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.YueDanBean
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.widget.YueDanItemView

/**
 * Created by Administrator on 2018/4/23.
 * 悦单界面适配器
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getItemView(context: Context?): YueDanItemView {
        return YueDanItemView(context)
    }

    override fun refreshView(itemView: YueDanItemView, data: YueDanBean.PlayListsBean) {
        itemView.setData(data)
    }

}