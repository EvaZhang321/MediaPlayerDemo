package com.jingbanyun.mymediaplayer.ui.activity

import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.base.BaseActivity
import com.jingbanyun.mymediaplayer.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018/4/21.
 * 设置界面
 */
class SettingActivity : BaseActivity(),ToolBarManager {
    override val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        initSettingToolbar()
        //获取推送通知有没有选中
        val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val push = defaultSharedPreferences.getBoolean("push", false)
        println("push:"+push)
    }
}