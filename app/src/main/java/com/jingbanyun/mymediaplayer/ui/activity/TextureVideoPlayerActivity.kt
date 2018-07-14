package com.jingbanyun.mymediaplayer.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_player_texture.*

/**
 * Created by Administrator on 2018/4/24.
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {
    var videoPlayerBean:VideoPlayerBean? = null
    val mediaPlayer by lazy { MediaPlayer() }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //view大小发生变化
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        //视图更新
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
        //视图销毁
        //关闭mediaPlayer
        mediaPlayer?.let {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        return true
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //视图可用
        videoPlayerBean?.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface(p0))//这只播放视频画面
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                //旋转画面
                textureView.rotation = 100f
            }
        }
       }

    override fun getLayoutId(): Int {
        return R.layout.activity_player_texture
    }

    override fun initData() {
        //获取传递的数据
        videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        textureView.surfaceTextureListener = this
    }
}