package com.aiyakeji.mytest.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.DensityUtils

/**
 * @Author:CWQ
 * @DATE:2023/2/15
 * @DESC:
 */
class WaveProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val wavePaint = Paint()
    private val wavePath = Path()

    //是否上下颠倒
    private var rotateView = false
    private var mColor = Color.GREEN

    private var waveWidth = 0f
    private var waveHeight = 0f

    private var animator: ValueAnimator? = null
    private var dx: Float = 0f

    init {
        attrs?.let {
            initAttrs(it)
        }

        waveWidth = DensityUtils.dip2pxFloat(context, 200f)
        waveHeight = DensityUtils.dip2pxFloat(context, 40f)

        wavePaint.color = mColor
        wavePaint.isAntiAlias = true
        wavePaint.style = Paint.Style.FILL_AND_STROKE
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgressView)
        rotateView =
            typedArray.getBoolean(R.styleable.WaveProgressView_wave_progress_rotate, false)
        mColor =
            typedArray.getColor(R.styleable.WaveProgressView_wave_progress_color, Color.GREEN)
        typedArray.recycle()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(getWavePath(), wavePaint)
    }

    private fun getWavePath(): Path {
        wavePath.reset()
        if (rotateView) {
            wavePath.moveTo(-waveWidth + dx, height - waveHeight / 2)
        } else {
            wavePath.moveTo(-waveWidth + dx, waveHeight / 2)
        }

        var i = -waveWidth
        while (i <= width + waveWidth) {
            wavePath.rQuadTo(waveWidth / 4, -waveHeight / 2f, waveWidth / 2, 0f)
            wavePath.rQuadTo(waveWidth / 4, waveHeight / 2f, waveWidth / 2, 0f)
            i += waveWidth
        }
        if (rotateView) {
            wavePath.lineTo(width.toFloat(), 0f)
            wavePath.lineTo(0f, 0f)
        } else {
            wavePath.lineTo(width.toFloat(), height.toFloat())
            wavePath.lineTo(0f, height.toFloat())
        }

        wavePath.close()
        return wavePath
    }

    fun startAnim() {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, waveWidth).apply {
                duration = 2000
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                addUpdateListener {
                    dx = it.animatedValue as Float
                    postInvalidate()
                }
            }
        }
        animator?.start()
    }

    fun stopAnim() {
        animator?.cancel()
    }
}