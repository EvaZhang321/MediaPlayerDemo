package com.jingbanyun.mymediaplayer.ui.activity

import android.support.v4.view.ViewPager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.adapter.VideoPagerAdapter
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_player_jiecao.*


/**
 * Created by Administrator on 2018/4/24.
 */
class JiecaoVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_player_jiecao
    }

    override fun initData() {
        val data = intent.data
        if (data == null) {
            //从应用内部相应视频播放
            //获取传递的数据
            val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
            videoplayer.setUp(videoPlayerBean.url
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoPlayerBean.title)
        } else {
            //从外部应用相应视频播放
            if (data.toString().startsWith("http")) {
                //应用外网络视频请求  eg: http://hc.yinyuetai.com/uploads/videos/common/3F25015E4B9FF1B6A2D9B96662BCE556.mp4?sc=be6730ab286873b7&br=775&rd=Android
                videoplayer.setUp(data.toString()
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.toString())
            } else {
                //应用外本地视频请求  eg:/storage/emulated/0/QQBrowser/视频/81d5a7080226f368fa2f5bc2af76edb5_2.mp4
                videoplayer.setUp(data.path
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.path)
            }
        }
    }

    override fun initListener() {
        //适配viewpager
        viewpager.adapter = VideoPagerAdapter(supportFragmentManager)
        //radiogroup选中监听
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb1 -> viewpager.setCurrentItem(0)
                R.id.rb2 -> viewpager.setCurrentItem(1)
                R.id.rb3 -> viewpager.setCurrentItem(2)
            }
        }
        //viewpager选中状态监听
        viewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                //滑动状态改变的监听
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //滑动回调
            }

            override fun onPageSelected(position: Int) {
                //选中状态改变
                when (position) {
                    0->rg.check(R.id.rb1)
                    1->rg.check(R.id.rb2)
                    2->rg.check(R.id.rb3)
                }
            }

        })
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}