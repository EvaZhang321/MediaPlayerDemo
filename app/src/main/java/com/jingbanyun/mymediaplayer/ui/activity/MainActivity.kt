package com.jingbanyun.mymediaplayer.ui.activity

import android.support.v7.widget.Toolbar
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.util.FragmentUtil
import com.jingbanyun.mymediaplayer.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(),ToolBarManager {
    /**
     * by lazy 懒加载，使用的时候才会创建，并且是线程安全的，不用担心线程问题
     */
    override val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initMainToolBar()
    }

    override fun initListener() {
        //设置监听
        bottomBar.setOnTabSelectListener {
//            it 代表参数tabId
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,FragmentUtil.fragmentUtil.getFragment(it),it.toString())
            transaction.commit()
        }
    }
}
