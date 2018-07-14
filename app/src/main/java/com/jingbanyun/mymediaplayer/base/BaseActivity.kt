package com.jingbanyun.mymediaplayer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2018/4/20.
 * 所有Activity的基类
 */
abstract class BaseActivity:AppCompatActivity(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId()) //布局让子类来实现
        initListener()  //adapter 和 listener的初始化
        initData() //数据的支持
    }

    /**
     * 初始化数据
     */
   open protected fun initData() {
    }

    /**
     * adapter listener
     */
    open  protected fun initListener() {
    }

    /**
     * 获取布局id
     */
    abstract fun getLayoutId(): Int

    protected fun myToast(msg:String){
        runOnUiThread { toast(msg) }
    }

    /**
     * 开启acticity并且finish当前界面  reified ??? 具体化范型对象？获取范型的名字
     */
    inline fun<reified T:BaseActivity> startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}