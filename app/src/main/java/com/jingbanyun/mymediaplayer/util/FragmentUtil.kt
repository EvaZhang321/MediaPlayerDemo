package com.jingbanyun.mymediaplayer.util

import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseFragment
import com.jingbanyun.mymediaplayer.ui.fragment.HomeFragment
import com.jingbanyun.mymediaplayer.ui.fragment.MvFragment
import com.jingbanyun.mymediaplayer.ui.fragment.VBangFragment
import com.jingbanyun.mymediaplayer.ui.fragment.YueDanFragment

/**
 * Created by Administrator on 2018/4/21.
 * 单例的管理fragment的util类
 */
class FragmentUtil private constructor() {
    //私有化构造方法
    //by lazy懒加载，只会创建一次
    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { MvFragment() }
    val vbangFragment by lazy { VBangFragment() }
    val yueDanFragment by lazy { YueDanFragment() }

    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }

    /**
     * 根据tabId获取对应的fragment
     */
    fun getFragment(tabId: Int): BaseFragment? {
        when (tabId) {
            R.id.tab_home->return homeFragment
            R.id.tab_mv->return mvFragment
            R.id.tab_vbang->return vbangFragment
            R.id.tab_yuedan->return yueDanFragment
        }
        return null
    }
}