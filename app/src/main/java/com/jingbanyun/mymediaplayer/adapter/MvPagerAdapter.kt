package com.jingbanyun.mymediaplayer.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.itheima.player.model.bean.MvAreaBean
import com.jingbanyun.mymediaplayer.ui.fragment.MvPagerFragment

/**
 * Created by Administrator on 2018/4/24.
 */
class MvPagerAdapter(val context:Context,val list: List<MvAreaBean>?, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        //第一种数据传递方式
//        val fragment = MvPagerFragment()
//        val bundle = Bundle()
//        bundle.putString("args", list?.get(position)?.name)
//        fragment.arguments = bundle
        val bundle = Bundle()
        bundle.putString("args", list?.get(position)?.code)
        //第二种数据传递方式
        val fragment = Fragment.instantiate(context, MvPagerFragment::class.java.name, bundle)
        return fragment
    }

    override fun getCount(): Int {
        return list?.size?:0 //如果list不为空，返回list.size,如果为空，返回0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }
}