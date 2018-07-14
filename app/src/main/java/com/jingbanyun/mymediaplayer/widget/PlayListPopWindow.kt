package com.jingbanyun.mymediaplayer.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.jingbanyun.mymediaplayer.R

class PlayListPopWindow(context :Context,adapter: BaseAdapter,listener:AdapterView.OnItemClickListener,var window:Window):PopupWindow() {
    //记录当前应用程序窗体的透明度
    var alpha:Float = 0f
    init {
        //记录当前应用程序窗体的透明度
        alpha = window.attributes.alpha
        //设置布局
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_laylist,null,false)
        //获取listview
        val listView = contentView.findViewById<ListView>(R.id.listview)
        //适配
        listView.adapter = adapter
        //设置列表条目点击事件
        listView.setOnItemClickListener(listener)
        //设置宽度和高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point  = Point()
        windowManager.defaultDisplay.getSize(point)
        height = (point.y*3)/5
        //设置获取焦点
        isFocusable = true
        //设置外部点击
        isOutsideTouchable= true
        //能够响应返回按钮(低版本popwindow点击返回按钮能够响应)
        setBackgroundDrawable(ColorDrawable())
        //处理popWindow动画
        animationStyle = R.style.pop
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        //popwindow已经显示
        val attributes = window.attributes
        attributes.alpha = 0.3f
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        //popwindows隐藏
        val attributes = window.attributes
        attributes.alpha = 1f
        window.attributes = attributes
    }
}