package com.jingbanyun.mymediaplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.HomeItemBean
import com.jingbanyun.mymediaplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by Administrator on 2018/4/21.
 */
class HomeItemView:RelativeLayout {
    //代码中new出来的时候使用
    constructor(context: Context?) : super(context)
    //布局文件中使用
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    //和主题相关时候使用
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 初始化方法
     */
    init{
       View.inflate(context, R.layout.item_home,this)
    }

    /**
     * 刷新条目view数据
     */
    fun setData(data: HomeItemBean) {
        //歌曲名称
        title.setText(data.title)
        //简介
        desc.setText(data.description)
        //背景图片
        Picasso.get().load(data.posterPic).into(bg);
    }
}