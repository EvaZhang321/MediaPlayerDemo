package com.jingbanyun.mymediaplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jingbanyun.mymediaplayer.R
import com.jingbanyun.mymediaplayer.model.LyricBean
import com.jingbanyun.mymediaplayer.util.LyricLoader
import com.jingbanyun.mymediaplayer.util.LyricUtil
import org.jetbrains.anko.doAsync

/**
 * 自定义歌词view
 */
class LyricView : View {
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }//通过惰性加载创建画笔paint
    val list by lazy { ArrayList<LyricBean>() }
    var centerLine = 0
    private var viewW: Int = 0
    private var viewH: Int = 0
    private var bigSize = 0f
    private var smallSize = 0f
    private var white = 0
    private var green = 0
    private var lineHeight = 0
    var _duration = 0 //歌曲总时长
    var progress = 0 //歌曲播放进度
    var offsetY = 0f // 偏移y
    var updateByProcess = true //指定是否可以通过progress进度更新歌词

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)
        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)

        //画笔
        paint.textAlign = Paint.Align.CENTER  //在x方向确定位置是通过中间位置确定坐标

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (list.size == 0) {
            //歌词没有加载
            drawSingleLine(canvas)
        } else {
            //歌词已经加载
            drawMultipleLine(canvas)
        }

    }

    /**
     * 绘制多行居中文本
     */
    private fun drawMultipleLine(canvas: Canvas?) {
        if (updateByProcess) {
            //求居中行偏移y
            //行可用时间：
            var lineTime: Int
            if (centerLine == list.size - 1) {
                //1.最后一行居中：行可用时间 = duration - 最后一行开始时间
                lineTime = _duration - list.get(centerLine).startTime
            } else {
                //2.其他行居中：行可用时间 =下一行开始时间 - 居中行开始时间
                val centerS = list.get(centerLine).startTime
                val nextS = list.get(centerLine + 1).startTime
                lineTime = nextS - centerS
            }

            //偏移时间 = progress - 居中行开始时间
            var offsetTime = progress - list.get(centerLine).startTime
            //偏移百分比=偏移时间/行可用时间
            var offsetPercent = offsetTime / lineTime.toFloat()
            //偏移y = 偏移百分比*行高
            offsetY = offsetPercent * lineHeight
        }

        val centerText = list.get(centerLine).content
        val bounds = Rect()
        paint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()
        val centerY = viewH / 2 + textH / 2 - offsetY
        for ((index, value) in list.withIndex()) {
            if (index == centerLine) {
                paint.textSize = bigSize
                paint.color = green
            } else {
                paint.textSize = smallSize
                paint.color = white
            }
            val curX = viewW / 2
            val curY = (index - centerLine) * lineHeight + centerY

            //超出边界处理
            //处理超出上边界歌词
            if (curY < 0) continue
            //超出下边界
            if (curY > viewH + lineHeight) break

            val curText = list[index].content
            canvas?.drawText(curText, curX.toFloat(), curY.toFloat(), paint)
        }
    }

    /**
     * 绘制单行居中文本
     */
    private fun drawSingleLine(canvas: Canvas?) {
        //初始化paint颜色和大小
        paint.textSize = bigSize
        paint.color = green

        val text = "正在加载歌词..."
        //求文本的宽度和高度
        var bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()

        //x = viewW/2-textW/2
        //        val x = viewW/2 - textW/2
        //y = viewH/2+textH/2
        val y = viewH / 2 + textH / 2

        //绘制内容
        canvas?.drawText(text, viewW / 2.toFloat(), y.toFloat(), paint)
    }

    /**
     * 布局（onLayout）完成之后执行
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewW = w
        viewH = h
    }

    /**
     * 传递当前播放进度 实现歌词播放
     */
    fun updateProgress(progress: Int) {
        //初始播放，第0行选中
       if(progress==0) centerLine = 0
        if (!updateByProcess) return
        if (list.size == 0) return

        this.progress = progress
        //获取居中行行号
        //先判断是否是最后一行
        if (progress >= list[list.size - 1].startTime) {
            //progress>=最后一行开始时间 最后一行居中
            centerLine = list.size - 1
        } else {
            //其他行居中 循环遍历集合
            for (index in 0 until list.size - 1) {
                //progress >= 当前行开始时间&progress<下一行开始时间
                val curStartTime = list[index].startTime
                val nextStartTime = list[index + 1].startTime
                if (progress >= curStartTime && progress < nextStartTime) {
                    centerLine = index
                    break
                }
            }
        }

        //找到居中行之后绘制当前多行歌词
//        view的刷新有三种方式
        invalidate() //onDraw方法
//        postInvalidate() //onDraw方法 可以在子线程中刷新
//        requestLayout() //view布局参数改变时刷新
    }


    /**
     * 设置当前播 放歌曲总时长
     */
    public fun setSongDuration(duration: Int) {
        this._duration = duration
    }

    /**
     * 设置歌曲播放名称
     * 解析歌词文件并添加到集合中
     */
    fun setSongName(name: String) {
        doAsync {
            val file = LyricLoader.loadLyricFile(name)
            val list1 = LyricUtil.parseLyric(file)
            this@LyricView.list.clear()
            this@LyricView.list.addAll(list1)
        }
    }

    var downY = 0f
    var markY = 0f
    /**
     * 歌词控件手势事件处理
     * 1.手指按下时，停止通过进度更新歌词
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    //停止通过进度更新歌词
                    updateByProcess = false
                    //记录手指按下的y
                    downY = event.y
                    //记录原来进度已经更新的偏移y
                    markY = this.offsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    //当前y
                    val endY = event.y
                    val offY = downY - endY
                    this.offsetY = offY + markY
                    //如果最终的y的偏移大于行高 重新确定居中行
                    if (Math.abs(this.offsetY) >= lineHeight) {
                        //求居中行行号偏移
                        val offsetLine = (this.offsetY / lineHeight).toInt()
                        centerLine += offsetLine

                        //对居中行做边界处理
                        if (centerLine < 0) centerLine = 0
                        else if (centerLine > list.size - 1) centerLine = list.size - 1
                        //downY重新设置
                        this.downY = endY
                        //重新确定偏移y
                        this.offsetY = this.offsetY % lineHeight
                        //重新记录y的偏移量
                        markY = this.offsetY

                        //更新播放进度
                        //方式一.let
//                        listener?.let {
//                            it(list.get(centerLine).startTime)
//                        }
                        //方式二:invoke
                        listener?.invoke(list.get(centerLine).startTime)

                    }

                    //重新绘制
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    //可以通过进度更新歌词
                    updateByProcess = true
                }
            }
        }
        return true
    }

    //进度回调函数
   private  var listener:((progress:Int)->Unit)?=null
    //设置进度回调函数
    fun setProgressListener(listener:((progress:Int)->Unit)){
        this.listener = listener
    }
}