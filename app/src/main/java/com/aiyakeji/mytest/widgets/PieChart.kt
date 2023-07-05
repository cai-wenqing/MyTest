package com.aiyakeji.mytest.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.aiyakeji.mytest.bean.PieData
import kotlin.math.cos
import kotlin.math.sin

/**
 * @Author:CWQ
 * @DATE:2023/7/4
 * @DESC:
 */
class PieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val TAG = "PieChart"

    private var mCenterX = 0f
    private var mCenterY = 0f
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRect = RectF()
    private val mOffsetDistance = 6f
    private var mPreValueAngle = 0f
    private var mMaxRadio = 0f
    private var mData = mutableListOf<PieData>()
    private var mMaxValue = 0f

    init {
        mPaint.style = Paint.Style.FILL
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mCenterX = measuredWidth / 2f
        mCenterY = measuredHeight / 2f
        mMaxRadio = mCenterX.coerceAtMost(mCenterY)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mData.isNotEmpty()) {
            var startAngle = 0f

            val rate = mMaxRadio / mMaxValue
            mData.forEach {
                it.angleRadio = it.value * rate
                mRect.set(
                    mCenterX - it.angleRadio,
                    mCenterY - it.angleRadio,
                    mCenterX + it.angleRadio,
                    mCenterY + it.angleRadio
                )
                offsetRect(mRect, startAngle + mPreValueAngle / 2)
                mPaint.color = it.color
                canvas?.drawArc(mRect, startAngle, mPreValueAngle, true, mPaint)
                startAngle += mPreValueAngle
            }
        }
    }


    private fun offsetRect(rectF: RectF, moveAngle: Float): RectF {
        val angle = moveAngle / 360 * 2 * Math.PI
        val dx = mOffsetDistance * cos(angle)
        val dy = mOffsetDistance * sin(angle)
        rectF.offset(dx.toFloat(), dy.toFloat())
        return rectF
    }


    fun setData(list: List<PieData>) {
        if (list.isNotEmpty()) {
            mData.clear()
            mData.addAll(list)

            mPreValueAngle = 360f / list.size

            mMaxValue = list[0].value
            list.forEach {
                if (it.value > mMaxValue) {
                    mMaxValue = it.value
                }
            }

            invalidate()
        }
    }
}