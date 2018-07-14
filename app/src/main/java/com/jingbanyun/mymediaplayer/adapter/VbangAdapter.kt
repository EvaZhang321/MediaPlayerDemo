package com.jingbanyun.mymediaplayer.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.jingbanyun.mymediaplayer.model.AudioBean
import com.jingbanyun.mymediaplayer.widget.VbangItemView

class VbangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    /**
     * 创建条目view
     */
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
        return VbangItemView(p0)
    }

    /**
     * view绑定数据
     */
    override fun bindView(p0: View?, p1: Context?, p2: Cursor?) {
        //view
        val itemView = p0 as VbangItemView
        //data
        val itemBean = AudioBean.getAudioBean(cursor)
        //view+data
        itemView.setData(itemBean)

    }
}