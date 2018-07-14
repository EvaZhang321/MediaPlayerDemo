package com.jingbanyun.mymediaplayer.service

import com.jingbanyun.mymediaplayer.model.AudioBean

interface Iservice {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgerss(): Int
    fun seekTo(p1: Int)
    fun updatePlayMode()
    fun getPlayMode(): Int
    fun playPre()
    fun playNext()
    fun getPlayList(): List<AudioBean>?
    fun playPosition(p2: Int)
}