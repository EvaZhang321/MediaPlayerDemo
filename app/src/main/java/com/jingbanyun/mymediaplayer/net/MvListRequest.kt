package com.jingbanyun.mymediaplayer.net

import com.itheima.player.model.bean.MvPagerBean
import com.jingbanyun.mymediaplayer.util.URLProviderUtils

/**
 * Created by Administrator on 2018/4/24.
 */
class MvListRequest(type: Int, code: String,offset: Int, handler: ResponseHandler<MvPagerBean>) : MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code,offset,20), handler) {
}