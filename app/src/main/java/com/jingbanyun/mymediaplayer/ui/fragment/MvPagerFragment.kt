package com.jingbanyun.mymediaplayer.ui.fragment

import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean
import com.jingbanyun.mymediaplayer.adapter.MvListAdapter
import com.jingbanyun.mymediaplayer.base.BaseListAdapter
import com.jingbanyun.mymediaplayer.base.BaseListFragment
import com.jingbanyun.mymediaplayer.base.BaseListPresenter
import com.jingbanyun.mymediaplayer.model.VideoPlayerBean
import com.jingbanyun.mymediaplayer.presenter.impl.MvListPresenterImpl
import com.jingbanyun.mymediaplayer.ui.activity.JiecaoVideoPlayerActivity
import com.jingbanyun.mymediaplayer.view.MvItemView
import com.jingbanyun.mymediaplayer.view.MvListView
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Administrator on 2018/4/24.
 * mv界面每一个页面fragment
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), MvListView {
    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun getList(response: MvPagerBean?): List<VideosBean>? {
        return response?.videos
    }

    //在fragment创建时传递数据不能通过构造方法，需要通过setArguments方法传递
    var code: String? = null

    override fun init() {
        //获取传递的数据
        code = arguments.getString("args")
    }

    override fun initListener() {
        super.initListener()
        //设置条目点击事件监听函数
        adapter.setMyListener {
//            startActivity<VideoPlayerActivity>("item" to VideoPlayerBean(it.id,it.title,it.url))
//            startActivity<TextureVideoPlayerActivity>("item" to VideoPlayerBean(it.id,it.title,it.url))
//            startActivity<VitamioVideoPlayerActivity>("item" to VideoPlayerBean(it.id,it.title,it.url))
//            startActivity<IjkVideoPlayerActivity>("item" to VideoPlayerBean(it.id,it.title,it.url))
            startActivity<JiecaoVideoPlayerActivity>("item" to VideoPlayerBean(it.id,it.title,it.url))
        }
    }
}