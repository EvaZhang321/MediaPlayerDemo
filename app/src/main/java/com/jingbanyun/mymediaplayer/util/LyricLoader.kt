package com.jingbanyun.mymediaplayer.util

import android.os.Environment
import java.io.File

/**
 * 歌词文件加载util
 */
object LyricLoader {
    //歌词文件
    val dir =File(Environment.getExternalStorageDirectory(),"Download/Lyric")

    /**
     * 根据歌曲文件加载歌词文件
     */
    fun loadLyricFile(display_name:String): File {
        return File(dir,display_name+".lrc")
    }
}