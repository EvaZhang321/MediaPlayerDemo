package com.jingbanyun.mymediaplayer.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.jingbanyun.mymediaplayer.base.BaseFragment

/**
 * 视频播发界面
 */
class DefaultFragment: BaseFragment() {
    override fun initView(): View? {
        val textView = TextView(context)
        textView.gravity= Gravity.CENTER
        textView.setTextColor(Color.RED)
        textView.text = javaClass.simpleName
        return textView
    }
}