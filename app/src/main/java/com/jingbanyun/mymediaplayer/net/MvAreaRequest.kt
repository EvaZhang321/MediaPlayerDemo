package com.jingbanyun.mymediaplayer.net

import com.itheima.player.model.bean.MvAreaBean
import com.jingbanyun.mymediaplayer.util.URLProviderUtils

/**
 * Created by Administrator on 2018/4/24.
 */
class MvAreaRequest(handler: ResponseHandler<List<MvAreaBean>>) : MRequest<List<MvAreaBean>>(0, URLProviderUtils.getMVareaUrl(), handler) {
}