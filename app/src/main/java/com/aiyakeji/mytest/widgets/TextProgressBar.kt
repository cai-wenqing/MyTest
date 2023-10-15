package com.aiyakeji.mytest.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ProgressBar

/**
 * @Author:CWQ
 * @DATE:2023/5/22
 * @DESC:
 */
class TextProgressBar : ProgressBar {
    private var mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //文字右侧距当前进度右边缘间距
    private val mTextGap = 15f
    private val mTextBound = Rect()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mTextPaint.color = Color.WHITE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val textSize = measuredHeight.toFloat() / 2
        mTextPaint.textSize = textSize
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val text = "${(progress / max.toFloat() * 100).toInt()}%"
        mTextPaint.getTextBounds(text, 0, text.length, mTextBound)
        val progressWidth = width * (progress / max.toFloat())
        if (mTextBound.width() <= progressWidth - mTextGap) {
            canvas?.drawText(
                text,
                progressWidth - mTextBound.width() - mTextGap,
                height.toFloat() / 2 + mTextBound.height() / 2,
                mTextPaint
            )
        }
    }
}