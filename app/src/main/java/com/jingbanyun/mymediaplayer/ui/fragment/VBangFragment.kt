package com.jingbanyun.mymediaplayer.ui.fragment

import android.Manifest
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.AdapterView
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.adapter.VbangAdapter
import com.jingbanyun.mymediaplayer.base.BaseFragment
import com.jingbanyun.mymediaplayer.model.AudioBean
import com.jingbanyun.mymediaplayer.ui.activity.AudioPlayerActivity
import com.jingbanyun.mymediaplayer.util.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbanf.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

/**
 * Created by Administrator on 2018/4/21.
 */
class VBangFragment : BaseFragment() {
/*    val handler = object: Handler(){
        override fun handleMessage(msg: Message?) {
            msg?.let {
                val cursor = msg.obj as Cursor
                //打印所有数据
                CursorUtil.logCursor(cursor)
            }
        }
    }*/

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_vbanf, null)
    }

    var adapter:VbangAdapter?=null

    override fun initListener() {
        adapter = VbangAdapter(context,null)
        listview.adapter = adapter
        //设置条目点击事件
        listview.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            //获取数据集合
            val cursor = adapter?.getItem(i) as Cursor
            //通过当前位置的cursor获取整个播放列表
            val list = AudioBean.getAudioBeans(cursor)
            //跳转到音乐播放界面
            startActivity<AudioPlayerActivity>("list" to list,"position" to i)
        }
    }

    override fun initData() {
        //加载音乐列表数据
//        loadSongs()
        //动态申请权限
        handlePermission()

    }

    private fun handlePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        //查看是否有权限
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取权限
            loadSongs()
        }else{
            //没有获取权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //需要弹出 解释为什么需要权限
                alert("我只搜索音频文件，给下权限吧","友情提示"){
                    yesButton { myRequestPermission() }
                    noButton {  }
                }.show()
            }else{
                //不需要弹出 系统提示了（是吗，不需要我们的弹窗解释）
                myRequestPermission()
            }
        }
    }

    /**
     * 真正申请权限
     */
    private fun myRequestPermission() {
        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permission,1)
    }

    /**
     * 接受权限授权结果
     * requestCode 请求码
     * permission 申请权限数组
     * grantResult 申请之后结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        }
    }


    private fun loadSongs() {
        //方法一：开启线程查询音乐数据
        //        Thread(object : Runnable {
        //            override fun run() {
        //                val resolver = context.contentResolver
        //                val cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Audio.Media.DATA,
        //                        MediaStore.Audio.Media.SIZE,
        //                        MediaStore.Audio.Media.DISPLAY_NAME,
        //                        MediaStore.Audio.Media.ARTIST),
        //                        null, null, null)
        //                val msg = Message.obtain()
        //                msg.obj = cursor
        //                handler.sendMessage(msg)
        //            }
        //        }).start()

        //方法二：asynctask
        //        AudioTask().execute(context.contentResolver)

        //方法三：AsyncQueryHandler
        val handler = object : AsyncQueryHandler(context.contentResolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成回调 主线程中
                //打印数据
    //                CursorUtil.logCursor(cursor)
                //刷新列表 这个刷新cursorAdapter不行
    //                (cookie as VbangAdapter).notifyDataSetChanged()
                //设置数据源
                //刷新adapter
                (cookie as VbangAdapter).swapCursor(cursor)
            }
        }
        //开始查询
        handler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST),
                null, null, null)
    }

    /**
     * 音乐查询异步任务
     */
    class AudioTask: AsyncTask<ContentResolver,Void,Cursor>(){
        /**
         * 后台执行任务 新线程
         */
        override fun doInBackground(vararg p0: ContentResolver?): Cursor? {
            val cursor = p0[0]?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.SIZE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ARTIST),
                    null, null, null)
            return cursor
        }

        /**
         * 将后台任务结果回调到主线程中
         */
        override fun onPostExecute(result: Cursor?) {
            //打印cursor
                CursorUtil.logCursor(result)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁 关闭cursor
        //获取adpter中的cursor关闭
        //将adapter中的cursor设置为null
        adapter?.changeCursor(null)
    }
}