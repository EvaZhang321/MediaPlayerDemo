package com.jingbanyun.mymediaplayer.ui.activity

import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_player_ijk.*

/**
 * Created by Administrator on 2018/4/24.
 */
class IjkVideoPlayerActivity :BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_player_ijk
    }

    override fun initData() {
        //获取传递的数据
        val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        videoView.setVideoPath(videoPlayerBean.url)
        videoView.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        //停止播放
        videoView.stopPlayback()
    }
}