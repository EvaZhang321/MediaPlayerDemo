package com.jingbanyun.mymediaplayer.ui.activity

import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import io.vov.vitamio.Vitamio
import kotlinx.android.synthetic.main.activity_player_vitamio.*

/**
 * Created by Administrator on 2018/4/24.
 */
class VitamioVideoPlayerActivity :BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_player_vitamio
    }

    override fun initData() {
        //初始化vitamio
//        io.vov.vitamio.LibsChecker.checkVitamioLibs(this)
        Vitamio.isInitialized(applicationContext)

        //获取传递的数据
        val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        videoView.setVideoPath(videoPlayerBean.url)
        videoView.start()
    }
}