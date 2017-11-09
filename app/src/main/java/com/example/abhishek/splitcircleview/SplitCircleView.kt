package com.example.abhishek.splitcircleview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

class SplitCircleView : View {

    var i: Int = 0
    private val basePaint = Paint()
    private val progressPaint = Paint()
    private val textPaint = Paint()
    private val lineWidthMultiplier = 0.80

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        basePaint.color = Color.LTGRAY
        basePaint.style = Paint.Style.STROKE
        basePaint.strokeWidth = 5f
        basePaint.isAntiAlias = true

        progressPaint.color = Color.RED
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = 5f
        progressPaint.isAntiAlias = true

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.color = Color.BLACK
        textPaint.textSize = 40f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = this.width
        val height = this.height
        val centerX = width / 2
        val centerY = height / 2

        val circleRadius = centerX / 2

        for (i in 0..360 step 5) {
            drawSegmentOnCanvas(canvas, i, centerX, centerY, circleRadius, basePaint)
        }

        if (i <= 360) {
            for (j in 0..i step 5) {
                setColorToPaintForProgress(j)
                drawSegmentOnCanvas(canvas, j, centerX, centerY, circleRadius, progressPaint)
            }
            canvas.drawText(i.toString(), centerX.toFloat(), centerY.toFloat(), textPaint)
            i += 5
            if (i < 360) postInvalidateDelayed(getSpeed(i))
        }
    }

    private fun drawSegmentOnCanvas(canvas: Canvas, i: Int, centerX: Int, centerY: Int, circleRadius: Int, paint: Paint) {
        val radians = Math.toRadians(i.toDouble())
        val x1 = circleRadius * lineWidthMultiplier * Math.cos(radians) + centerX
        val y1 = circleRadius * lineWidthMultiplier * Math.sin(radians) + centerY
        val x2 = circleRadius * Math.cos(radians) + centerX
        val y2 = circleRadius * Math.sin(radians) + centerY
        canvas.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    private fun getSpeed(i: Int) = (i / 10).toLong()

    private fun setColorToPaintForProgress(i: Int) =
            when (i) {
                in 0..90 -> progressPaint.color = Color.GREEN
                in 90..180 -> progressPaint.color = Color.YELLOW
                in 180..270 -> progressPaint.color = Color.RED
                else -> progressPaint.color = Color.GRAY
            }
}
