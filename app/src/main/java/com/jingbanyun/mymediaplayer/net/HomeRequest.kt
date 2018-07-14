package com.jingbanyun.mymediaplayer.net

import com.itheima.player.model.bean.HomeItemBean
import com.jingbanyun.mymediaplayer.util.URLProviderUtils

/**
 * Created by Administrator on 2018/4/23.
 * 首页数据的请求类
 */
class HomeRequest(type:Int,offest: Int,handler: ResponseHandler<List<HomeItemBean>>) :
        MRequest<List<HomeItemBean>>(type,URLProviderUtils.getHomeUrl(offest,20),handler) {
}