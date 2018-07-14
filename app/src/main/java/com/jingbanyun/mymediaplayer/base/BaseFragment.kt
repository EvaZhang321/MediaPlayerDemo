package com.jingbanyun.mymediaplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2018/4/20.
 * 所有fragment的基类
 */
abstract class BaseFragment:Fragment(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }

    /**
     * fragment初始化
     */
   open protected fun init() {}

    /**
     * 获取布局的is
     */
    abstract fun initView(): View?


    /**
     * adapter
     * listener
     */
   open protected fun initListener() {}

    /**
     * 数据的初始化
     */
    open  protected fun initData() {}

    fun myToast(msg:String){
        context.runOnUiThread { toast(msg) }
    }
}