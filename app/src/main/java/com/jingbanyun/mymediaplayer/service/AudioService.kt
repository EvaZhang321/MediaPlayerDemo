package com.jingbanyun.mymediaplayer.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.model.AudioBean
import com.jingbanyun.mymediaplayer.ui.activity.AudioPlayerActivity
import com.jingbanyun.mymediaplayer.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus
import java.util.*

class AudioService : Service() {
    var list: ArrayList<AudioBean>? = null
    var position: Int = -2 //当前正在播放的position
    var mediaPlayer: MediaPlayer? = null
    val binder by lazy { AudioBinder() }

    val sp by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }

    var notificationManager :NotificationManager ? = null
    var  notification :Notification ? = null

    val FROM_PRE = 1
    val FROM_NEXT = 2
    val FROM_STATE = 3
    val FROM_CONTENT = 4

    companion object {
        val MODE_ALL = 1
        val MODE_SINGLE = 2
        val MODE_RANDOM = 3
    }

    var mode = MODE_ALL

    override fun onCreate() {
        super.onCreate()
        //获取播放模式
        mode = sp.getInt("mode", MODE_ALL)
    }

    /**
     * 多次开启，会执行多次
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //判断进入service方式
        val from = intent?.getIntExtra("from", -1)
        when(from){
            FROM_PRE->{binder.playPre()}
            FROM_NEXT->{binder.playNext()}
            FROM_CONTENT->{ binder.notifyUpdateUi()}
            FROM_STATE->{binder.updatePlayState()}
                else->{
                    val pos = intent?.getIntExtra("position", -1) ?: -1 //想要播放的position
                    if (pos != position) { //想要播放的条目和正在播放的条目不是同一首
                        position = pos
                        //获取集合及position
                        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                        //开始播放音乐
                        binder.playItem()
                    }else{
                        //通知界面更新
                        binder.notifyUpdateUi()
                    }
                }
        }


        //返回值：
        //START_STICKY 粘性的 service强制杀死之后 会尝试重新启动service 不会传递原来的intent(null)
        //START_NOT_STICKY 非粘性的 service强制杀死之后 不会尝试重新启动service
        //START_REDELIVER_INTENT service强制杀死之后 会尝试重新启动service 会传递原来的intent
        return START_NOT_STICKY
    }

    /**
     * 多次绑定，只会执行一次
     */
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    inner class AudioBinder : Binder(), Iservice, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         * 播放指定位置歌曲
         */
        override fun playPosition(p2: Int) {
            this@AudioService.position = p2
            playItem()
        }

        /**
         * 获取播放集合
         */
        override fun getPlayList(): List<AudioBean>? {
            return list
        }

        /**
         * 播放上一首
         */
        override fun playPre() {
            list?.let {
                //获取要播放歌曲position
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size - 1)
                    else -> {
                        if (position == 0) {
                            position = it.size - 1
                        } else {
                            position--
                        }
                    }
                }
                playItem()
            }

        }

        /**
         * 播放下一首
         */
        override fun playNext() {
            list?.let {
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size - 1)
                    else -> position = (position + 1) % it.size
                }
                playItem()
            }

        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode(): Int {
            return mode
        }

        /**
         * 修改播放模式
         * 切换顺序：MODE_ALL -> MODE_SINGLE -> MODE_RANDOM
         */
        override fun updatePlayMode() {
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }
            //保存播放模式
            sp.edit().putInt("mode", mode).commit()
        }

        /**
         * 歌曲播放完成之后回调
         */
        override fun onCompletion(p0: MediaPlayer?) {
            //自动播放下一曲
            autoPlayNext()
        }

        /**
         * 根据播放模式自动播放下一曲
         */
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> {
                    list?.let {
                        position = (position + 1) % it.size
                    }
                }
//              MODE_SINGLE -> 单曲循环 position不需要变化
                MODE_RANDOM -> {
                    list?.let {
                        position = Random().nextInt(it.size)
                    }
                }
            }

            playItem()
        }

        /**
         * 跳转到当前进度播放
         */
        override fun seekTo(p1: Int) {
            mediaPlayer?.seekTo(p1)
        }

        /**
         * 获取当前播放进度
         */
        override fun getProgerss(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        /**
         * 获取总进度
         */
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        /**
         * 更新播放状态
         */
        override fun updatePlayState() {
            //获取当前播放状态
            val isPlaying = isPlaying()
            //切换播放状态
            isPlaying?.let {
                if (isPlaying) {
                    //播放-->暂停
                    pause()
                } else {
                    //暂停-->播放
                    start()
                }
            }

        }

        /**
         * 暂停
         */
        private fun pause() {
            mediaPlayer?.pause()
            EventBus.getDefault().post(list?.get(position))
            //更新图标
            notification?.contentView?.setImageViewResource(R.id.state,R.mipmap.btn_audio_pause_normal)
            notificationManager?.notify(1,notification)
        }

        /**
         * 播放
         */
        private fun start(){
            mediaPlayer?.start()
            EventBus.getDefault().post(list?.get(position))
            //更新图标
            notification?.contentView?.setImageViewResource(R.id.state,R.mipmap.btn_audio_play_normal)
            //重新显示
            notificationManager?.notify(1,notification)
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mediaPlayer: MediaPlayer?) {
            //开始播放
            mediaPlayer?.start()
            //通知界面更新
            notifyUpdateUi()
            //显示通知
            showNotification()
        }

        private fun showNotification() {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification = getNotification()
            notificationManager?.notify(1, notification)
        }

        private fun getNotification(): Notification? {
            val notification = NotificationCompat.Builder(this@AudioService)
                    .setTicker("正在播放歌曲${list?.get(position)?.display_name}")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomContentView(getRemoteView())//自定义通知view
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true)//设置不能滑动删除通知
                    .setContentIntent(getPendingIntent())//通知栏主体点击事件
                    .build()
        return notification
        }

        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService, MainActivity::class.java)
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)
            intentA.putExtra("from",FROM_CONTENT)
            val intents = arrayOf(intentM,intentA)
            return PendingIntent.getActivities(this@AudioService, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getRemoteView(): RemoteViews? {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            //修改标题和内容
            remoteViews.setTextViewText(R.id.title,"${list?.get(position)?.display_name}")
            remoteViews.setTextViewText(R.id.artist,"${list?.get(position)?.artist}")
            //处理上一曲 下一曲 状态点击事件
            remoteViews.setOnClickPendingIntent(R.id.pre,getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state,getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next,getNextPendingIntent())
         return   remoteViews
        }

        /**
         * 下一曲点击事件
         */
        private fun getNextPendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from",FROM_NEXT)
            return PendingIntent.getService(this@AudioService, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        /**
         * 播放暂停按钮点击事件
         */
        private fun getStatePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from",FROM_STATE)
            return PendingIntent.getService(this@AudioService, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        /**
         * 上一曲点击事件
         */
        private fun getPrePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from",FROM_PRE)
            return PendingIntent.getService(this@AudioService, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        /**
         * 通知界面更新
         */
        fun notifyUpdateUi() {
            //发送端
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            //如果mediaPlayer已经存在就先释放掉
            if (mediaPlayer != null) {
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
            }
        }
    }
}