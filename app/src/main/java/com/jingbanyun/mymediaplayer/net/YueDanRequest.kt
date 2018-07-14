package com.jingbanyun.mymediaplayer.net

import com.itheima.player.model.bean.YueDanBean
import com.jingbanyun.mymediaplayer.util.URLProviderUtils

/**
 * Created by Administrator on 2018/4/23.
 *  悦单界面网络请求request
 */
class YueDanRequest(type:Int,offset:Int,handler:ResponseHandler<YueDanBean>):MRequest<YueDanBean>(type,URLProviderUtils.getYueDanUrl(offset,20),handler) {
}