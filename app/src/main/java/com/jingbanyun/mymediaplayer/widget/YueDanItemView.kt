package com.jingbanyun.mymediaplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.YueDanBean
import com.jingbanyun.mymediaplayer.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * Created by Administrator on 2018/4/23.
 * 悦单界面每个条目的自定义view
 */
class YueDanItemView:RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan,this)
    }

    /**
     * 条目view的控件初始化
     */
    fun setData(data: YueDanBean.PlayListsBean) {
        //歌单名称
        title.text = data.title
        //创建者
        author_name.text = data.creator?.nickName
        //歌曲个数
        count.text = data.videoCount.toString()
        //背景
        Picasso.get().load(data.playListBigPic).into(bg)
        //创建者头像
        Picasso.get().load("http:"+data.creator?.largeAvatar).transform(CropCircleTransformation()).into(author_image)
    }
}