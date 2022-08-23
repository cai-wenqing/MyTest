package com.aiyakeji.mytest.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @Author:CWQ
 * @DATE:2022/7/27
 * @DESC:
 */
class VolumeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val TAG = "VolumeView"

    //竖线个数
    private val LINE_NUM = 11

    private var mWidth = 0
    private var mHeight = 0f

    //竖线高度下限
    private var mMinLineHeight = 0f

    //线宽
    private val mLineWidth = 10f

    //竖线横向间隔
    private var mLineHorizontalGap = 0f

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLineList = arrayListOf<RectF>()
    private var mLineAnim: ValueAnimator? = null

    init {
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        for (i in 0 until LINE_NUM) {
            mLineList.add(RectF())
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight.toFloat()
        mLineHorizontalGap = (mWidth - (LINE_NUM * mLineWidth)) / (LINE_NUM - 1)
        mMinLineHeight = mHeight / 3

        calcLineHeight(0f)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mLineList.forEach {
            canvas.drawRoundRect(it, 10f, 10f, mPaint)
        }
    }


    /**
     * 计算各竖线坐标
     * @param angle Float
     */
    private fun calcLineHeight(angle: Float) {
        for (i in 0 until mLineList.size) {
            val angleValue =
                Math.sin(2 * Math.PI / (mLineList.size - 1) * Math.abs(mLineList.size / 2 - i) / 2 + angle)
            val lineHeight = Math.abs(angleValue) * (mHeight - mMinLineHeight) + mMinLineHeight
            mLineList[i].set(
                i * (mLineWidth + mLineHorizontalGap),
                ((mHeight - lineHeight) / 2).toFloat(),
                i * (mLineWidth + mLineHorizontalGap) + mLineWidth,
                ((mHeight - lineHeight) / 2 + lineHeight).toFloat()
            )
        }
    }


    private fun startLineAnim() {
        if (mLineAnim == null) {
            mLineAnim = ValueAnimator.ofFloat(0f, Math.PI.toFloat()).apply {
                duration = 800
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                addUpdateListener { animation: ValueAnimator ->
                    val currentValue: Float = animation.animatedValue as Float
                    calcLineHeight(currentValue)
                    invalidate()
                }
            }
        }
        mLineAnim?.start()
    }

    fun startAnim() {
        startLineAnim()
    }

    fun stop() {
        mLineAnim?.cancel()
    }
}