package com.jingbanyun.mymediaplayer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.VideosBean
import com.jingbanyun.mymediaplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mv.view.*

/**
 * Created by Administrator on 2018/4/24.
 * mv每一个界面条目view
 */
class MvItemView:RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_mv,this)
    }

    /**
     * 适配每一个条目的view
     */
    fun setData(data: VideosBean) {
        title.text = data.title
        artist.text = data.artistName
        Picasso.get().load(data.playListPic).into(bg)
    }
}