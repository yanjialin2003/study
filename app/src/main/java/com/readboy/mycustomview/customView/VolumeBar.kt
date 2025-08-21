package com.readboy.mycustomview.customView

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import com.readboy.mycustomview.R

class VolumeBar constructor(context: Context?, attributes: AttributeSet?, defaultAttrStyle: Int) : LinearLayout(context, attributes, defaultAttrStyle) {

    constructor(context: Context?, attributes: AttributeSet?) : this(context, attributes, 0)
    constructor(context: Context?) : this(context, null, 0)

    private val viewList = ArrayList<VolumeView>()
    private var tip = ""
    private var count = 0
    private var color = 0

    init {
        // 解析自定义属性
        val typedArray = context?.obtainStyledAttributes(attributes, R.styleable.VolumeBar)
        tip = typedArray?.getString(R.styleable.VolumeBar_tip) ?: ""
        count = typedArray?.getInt(R.styleable.VolumeBar_count, 20) ?: 20
        color = typedArray?.getColor(R.styleable.VolumeBar_col_color, context.resources.getColor(R.color.colorPrimary))
            ?: context!!.resources.getColor(R.color.colorPrimary)
        typedArray?.recycle()  //注意回收
        gravity = Gravity.CENTER_VERTICAL
        // 处理子 View
        initVolumeView()
    }

    private fun initVolumeView() {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.leftMargin = 10
        // 添加音量条子 View
        (0 until count).forEach { _ ->
            val view = VolumeView(context)
            addView(view, params)
            viewList.add(view)
        }
        // 添加文案
        val text = TextView(context)
        text.text = "$tip  0"
        addView(text, params)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 处理触摸事件
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                handleEvent(event)
            }
        }
        return true
    }

    // 触摸事件的处理逻辑，主要是在查找当前触摸事件的位置，确定是在第几个子 View 上，然后将此子 View 之前的所有子 View 都设置成实心的
    private fun handleEvent(event: MotionEvent) {

        val index = getCurIndex(event.x)
        // 设置子 View 为实心
        viewList.forEachIndexed { i, view ->
            if (i <= index) {
                view.full = true
                // 通知更新
                view.invalidate()
            }
        }
    }

    private fun getCurIndex(x: Float): Int {
        val pos = IntArray(2)
        var res = -1
        // 遍历子 View，确定当前触摸事件的位置
        viewList.forEachIndexed { index, view ->
            view.getLocationOnScreen(pos)
            if ((pos[0] + view.width) <= x) {
                res = index
            }
        }
        return res
    }
}