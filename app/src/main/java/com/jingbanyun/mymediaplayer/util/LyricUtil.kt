package com.jingbanyun.mymediaplayer.util

import com.jingbanyun.mymediaplayer.model.LyricBean
import java.io.File
import java.util.*

/**
 * 歌词解析util
 */
object LyricUtil {
    fun parseLyric(file: File):List<LyricBean>{
        //创建集合
        val list = ArrayList<LyricBean>()
        //判断歌词是否为空
        if (!file.exists()){
            list.add(LyricBean(0,"未找到歌词文件"))
            return list
        }
        //解析歌词文件 添加到集合中
        //java 写法
//        val bfr = BufferedReader(InputStreamReader(FileInputStream(file), "gbk"))
//        var line = bfr.readLine()
//        while (line != null){
//            //解析
//            //读取下一行
//            line = bfr.readLine()
//        }

        //kotlin 写法
        //读取歌词文件 返回每一行数据集合
        val linesList = file.readLines()
        for (line in linesList) {
            //解析一行
          var lineList:List<LyricBean> =  parseLine(line)
            //添加到集合中
            list.addAll(lineList)
        }
        //歌词排序
//        java写法Collections.sort()
        list.sortBy { it.startTime }
        //返回集合
        return list
    }

    /**
     * 解析一行歌词
     * [01:33.67 [02:46.87  伤心的泪儿谁来擦
     */
    private fun parseLine(line: String): List<LyricBean> {
        //创建集合
        val list = ArrayList<LyricBean>()
        //解析当前行
        val split = line.split("]")
        //获取歌词内容
        val content = split.get(split.size-1)
        for (index in 0 until split.size-1){
           val startTime:Int = parseTime(split.get(index))
            list.add(LyricBean(startTime,content))
        }

        //返回集合
        return list
    }

    /**
     * 解析时间
     * [ 00:01:33.67
     */
    private fun parseTime(get: String): Int {
        //[去掉
        val timeS = get.substring(1)
        //按照:切割
        val list = timeS.split(":")
        var hour = 0
        var min: Int
        var sec: Float
        if (list.size==3){
            //小时
            hour = list[0].toInt()*60*60*1000
            min = list[1].toInt()*60*1000
            sec = list[2].toFloat()*1000
        }else{
            //没有小时
            min = list[0].toInt()*60*1000
            sec = list[1].toFloat()*1000
        }
        return (hour+min+sec).toInt()
    }
}