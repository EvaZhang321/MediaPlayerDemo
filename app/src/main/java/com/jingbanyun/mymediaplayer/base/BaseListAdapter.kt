package com.jingbanyun.mymediaplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jingbanyun.mymediaplayer.widget.LoadMoreVie

/**
 * Created by Administrator on 2018/4/23.
 * 所有下拉刷新和上拉加载更多列表界面的adapter
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> : RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {

    private var list = ArrayList<ITEMBEAN>()
    //定义函数类型变量
    private var listener:((itemBean:ITEMBEAN)->Unit)? = null

    fun setMyListener(listener:((itemBean:ITEMBEAN)->Unit)){
        this.listener = listener
    }

    /**
     * 更新数据
     */
    fun updateList(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    /**
     * 加载更多数据
     */
    fun loadMore(list: List<ITEMBEAN>?) {
        //java的写法 if(list!=null)return
        //kotlin的写法：list不为空时候执行
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseListHolder {
        if (viewType == 1) {
            //最后一条
            return BaseListHolder(LoadMoreVie(parent?.context))
        } else {
            //普通条目
            return BaseListHolder(getItemView(parent?.context))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: BaseListAdapter.BaseListHolder?, position: Int) {
        //如果是最后一条 不需要刷新view
        if (position == list.size) return

        //条目数据
        val data = list.get(position)
        //条目view
        val itemView = holder?.itemView as ITEMVIEW
        //条目刷新
        refreshView(itemView, data)
        //设置条目点击事件
        itemView.setOnClickListener {
            listener?.let {
                it(data)
            }
//            listener?.invoke(data) 作用同上，当listener不为空的时候，invoke调用当前函数自己
        }
    }

    //java的点击事件 回调方法 需要接口包裹着 不可以单独传递
/*    private var listener = null

    interface onClickListener<ITEMBEAN> {
        fun onClick(data: ITEMBEAN)
    }

    fun setOnClickListener(listener: onClickListener<ITEMBEAN>) {
        this.listener = listener
    }*/

    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {
            //最后一条
            return 1
        } else {
            //普通条目
            return 0
        }
    }

    abstract fun getItemView(context: Context?): ITEMVIEW

    abstract fun refreshView(itemView: ITEMVIEW, data: ITEMBEAN)

    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}