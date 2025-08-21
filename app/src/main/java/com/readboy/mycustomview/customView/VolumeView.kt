package com.readboy.mycustomview.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.readboy.mycustomview.R

class VolumeView constructor(context: Context?, attributes: AttributeSet?, defaultAttrStyle: Int) : View(context, attributes, defaultAttrStyle) {
    val DEFAULT_LENGTH = 50
    var color: Int = 0
    var full: Boolean = false
    var paint: Paint = Paint()

    constructor(context: Context?, attributes: AttributeSet?) : this(context, attributes, 0)
    constructor(context: Context?) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(height / 5, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        color = if (color > 0)  color else context.resources.getColor(R.color.colorPrimary)
        paint.isAntiAlias = true
        paint.color = color
        if (full) {
            paint.style = Paint.Style.FILL
        } else {
            paint.style = Paint.Style.STROKE
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}