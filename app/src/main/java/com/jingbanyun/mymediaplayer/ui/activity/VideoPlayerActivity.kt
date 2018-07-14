package com.jingbanyun.mymediaplayer.ui.activity

import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_player.*

/**
 * Created by Administrator on 2018/4/24.
 */
class VideoPlayerActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_player
    }

    override fun initData() {
        //获取传递的数据
        val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        videoView.setVideoPath(videoPlayerBean.url)
        videoView.start()
    }
}