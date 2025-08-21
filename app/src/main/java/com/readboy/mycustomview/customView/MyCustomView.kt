package com.readboy.mycustomview.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.readboy.mycustomview.R

class MyCustomView : View {
    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            // 处理自定义属性
            val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView)
            val text = typedArray.getString(R.styleable.MyCustomView_customText)
            val color = typedArray.getColor(R.styleable.MyCustomView_customColor, Color.BLACK)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.setColor(Color.BLUE)
        canvas.drawCircle(
            (getWidth() / 2).toFloat(),
            (getHeight() / 2).toFloat(),
            50f,
            paint
        ) // 蓝色的圆
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> Log.d("TAG", "onTouchEvent: ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d("TAG", "onTouchEvent: ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.d("TAG", "onTouchEvent: ACTION_UP")
        }
        return true
    }
}
