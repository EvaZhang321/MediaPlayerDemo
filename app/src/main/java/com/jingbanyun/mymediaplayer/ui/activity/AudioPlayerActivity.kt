package com.jingbanyun.mymediaplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.adapter.PopAdapter
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.model.AudioBean
import com.jingbanyun.mymediaplayer.service.AudioService
import com.jingbanyun.mymediaplayer.service.Iservice
import com.jingbanyun.mymediaplayer.util.StringUtil
import com.jingbanyun.mymediaplayer.widget.PlayListPopWindow
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {
    /**
     * 弹出的播放列表条目点击事件
     */
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        //播放当前点击的歌曲
        iService?.playPosition(p2)
    }

    /**
     * 进度改变回调
     * p1:改变之后的进度
     * p2:true通过用户手指拖动改变进度 false代表通过代码方式改变进度
     */
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        //判断是否是用户操作
        if (!p2) return
        //更新播放进度
        iService?.seekTo(p1)
        //更新界面进度显示
        updateProgress(p1)
    }

    /**
     * 手指触摸seekbar回调
     */
    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    /**
     * 手指离开seekbar回调
     */
    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    var audioBean: AudioBean? = null

    private val animationDrawable: AnimationDrawable
        get() {
            return audio_anim.drawable as AnimationDrawable
        }

    var duration: Int = 0
    val MSG_PROGRESS = 0
    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.state -> updatePlayState()
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }

    /**
     * 显示播放列表
     */
    private fun showPlayList() {
        val playList = iService?.getPlayList()
        playList?.let {
            //获取底部高度
            val bottomH = audio_player_bottom.height
            val popWindow = PlayListPopWindow(this,PopAdapter(it),this,window)
            popWindow.showAsDropDown(audio_player_bottom,0,-bottomH)
        }
    }

    /**
     * 更新播放模式
     */
    private fun updatePlayMode() {
        //修改service中的mode
        iService?.updatePlayMode()
        //修改界面模式图标
        updatePlayModeButton()
    }

    /**
     * 根据当前播放模式修改播放模式图标
     */
    private fun updatePlayModeButton() {
        iService?.let {
            //获取播放模式
            val modeI: Int = it.getPlayMode()
            //设置图标
            when (modeI) {
                AudioService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
                AudioService.MODE_SINGLE -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
                AudioService.MODE_RANDOM -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
        }
    }

    /**
     * 接收EventBus方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveMsg(itemBean: AudioBean) {
        //记录下播放歌曲bean
        this.audioBean = itemBean
        //更新歌曲歌手名称
        audio_title.text = itemBean.display_name
        artist.text = itemBean.artist
        //设置播放歌曲名称
        lyricView.setSongName(itemBean.display_name)
        //更新播放状态按钮
        updatePlayStateButton()
        //动画播放 ImageView设置的是src获取方式
        val drawable = animationDrawable
        //动画播放 ImageView设置的是background获取方式
//        val drawable = audio_anim.background as AnimationDrawable
        drawable.start()
        //获取总进度
        duration = iService?.getDuration() ?: 0
        //进度条设置进度最大值
        progress_sk.max = duration
        //设置歌曲播放总进度
        lyricView.setSongDuration(duration)
        //更新播放进度 移到更新播放按钮状态，因为与播放状态保持着一致
//        startUpdateProgress()
        //更新播放模式图标
        updatePlayModeButton()
    }

    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        //获取当前进度
        val process: Int = iService?.getProgerss() ?: 0
        //更新进度更新
        updateProgress(process)
        //定时更新进度
        handler.sendEmptyMessage(MSG_PROGRESS)
    }

    /**
     * 根据当前进度数据更新界面
     */
    private fun updateProgress(process: Int) {
        //更新进度数值
        progress.text = StringUtil.parseDuration(process) + "/" + StringUtil.parseDuration(duration)
        //更新进度条
        progress_sk.progress = process
        //更新歌词播放进度
        lyricView.updateProgress(process)
    }

    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新播放状态
        iService?.updatePlayState()
        //更新播放图标
        updatePlayStateButton()
    }

    /**
     * 根据播放状态来更新图标
     */
    private fun updatePlayStateButton() {
        //获取当前播放状态
        val isPlaying = iService?.isPlaying()
        isPlaying?.let {
            //根据状态更新图标
            if (isPlaying) {
                //播放
                state.setImageResource(R.drawable.selector_btn_audio_play)
                //开始播放动画
                animationDrawable.start()
                //开始更新进度
                handler.sendEmptyMessageDelayed(MSG_PROGRESS, 1000)
            } else {
                //暂停
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                //停止播放动画
                animationDrawable.stop()
                //停止更新进度
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener { finish() }
        //进度条变化监听
        progress_sk.setOnSeekBarChangeListener(this)
        //播放模式点击事件
        mode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        //播放列表
        playlist.setOnClickListener(this)
        //歌词拖到进度更新监听
        lyricView.setProgressListener {
            //更新播放进度
            iService?.seekTo(it)
            //更新进度显示
            updateProgress(it)
        }
    }

    val conn by lazy { AudioConnection() }
    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        //注册EventBus
        EventBus.getDefault().register(this)
        val intent = intent
        //修改发件人和收件人
        intent.setClass(this, AudioService::class.java)
//        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
//        val position = intent.getIntExtra("position", -1)
        //通过audioservice播放音乐
//        val intent = Intent(this,AudioService::class.java)
        //通过intent将list集合以及当前的position传递过去
//        intent.putExtra("list",list)
//        intent.putExtra("position",position)

        //该项目必须先绑定
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        //再开启
        startService(intent)
    }

    var iService: Iservice? = null

    inner class AudioConnection : ServiceConnection {
        /**
         * 意外断开连接时，
         */
        override fun onServiceDisconnected(p0: ComponentName?) {
        }

        /**
         * servcie连接的时
         */
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            iService = p1 as Iservice
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑
        unbindService(conn)
        if (EventBus.getDefault().isRegistered(this)) {
            //反注册EventBus
            EventBus.getDefault().unregister(this)
        }
        //清空handler发送的所有消息 避免handler泄露
        handler.removeCallbacksAndMessages(null)
    }
}