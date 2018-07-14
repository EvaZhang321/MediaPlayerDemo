package com.jingbanyun.mymediaplayer.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.ui.activity.SettingActivity

/**
 * Created by Administrator on 2018/4/21.
 * 所有界面toolbar的管理类
 */
interface ToolBarManager {
    val toolbar: Toolbar;
    /**
     * 初始化主界面中的toolbar
     */
    fun initMainToolBar(){
        toolbar.setTitle("Eva的影音")
        toolbar.inflateMenu(R.menu.main)

        //如果java接口中只有一个为实现的方法，可以省略接口对象，直接用｛｝表示为实现的方法
        //下面两种写法等效
        toolbar.setOnMenuItemClickListener  {
                    when(it?.itemId) {
                        R.id.setting->{
                            //跳转到设置界面去
                            toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
                        }
                    }
                     true
                }

/*        toolbar.setOnMenuItemClickListener (object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId) {
                        R.id.setting->{
                            //跳转到设置界面去
                            toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
                        }
                    }
                    return true;
                }
            })*/
    }

    /**
     * 处理设置界面的toolbar
     */
    fun initSettingToolbar(){
        toolbar.setTitle("设置界面")
    }
}