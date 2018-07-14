package com.jingbanyun.mymediaplayer.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.model.AudioBean
import kotlinx.android.synthetic.main.item_vbang.view.*

class VbangItemView:RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_vbang,this)
    }


    fun setData(itemBean: AudioBean) {
        //歌曲名称
        title.text = itemBean.display_name
        artist.text = itemBean.artist
        size.text = Formatter.formatFileSize(context,itemBean.size)
    }

}