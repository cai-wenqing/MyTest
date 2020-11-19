package com.aiyakeji.mytest.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.aiyakeji.mytest.bean.Particle
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author CWQ
 * @date 2020/10/20
 */
class DimpleView : View {

    private var mWidth = 0f
    private var mHeight = 0f

    private val particleList = mutableListOf<Particle>()

    private val animator = ValueAnimator.ofFloat(0f, 1f)
    private val paint = Paint()
    private val path = Path()
    private val pathMeasure = PathMeasure()
    private val pos = FloatArray(2)
    private val tan = FloatArray(2)
    private val random = Random()
    private val particleNumber = 2000//粒子数量
    private val particleRadius = 2.2f//粒子半径
    private val diffusionRadius = 268f //粒子扩散圆半径

    init {
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updateParticle(it.animatedValue as Float)
            invalidate()
        }
        paint.color = Color.WHITE
        paint.isAntiAlias = true
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        path.addCircle(mWidth / 2, mHeight / 2, diffusionRadius, Path.Direction.CCW)
        pathMeasure.setPath(path, false)

        for (i in 0..particleNumber) {
            pathMeasure.getPosTan(i / particleNumber.toFloat() * pathMeasure.length, pos, tan)
            val nextX = pos[0] + random.nextInt(6) - 3f
            val nextY = pos[1] + random.nextInt(6) - 3f
            val angle = acos(((pos[0] - mWidth / 2) / diffusionRadius).toDouble())
            val speed = random.nextInt(2) + 0.5f
            val offset = random.nextInt(200).toFloat()
            val maxOffset = random.nextInt(250).toFloat()
            particleList.add(Particle(nextX, nextY, particleRadius, speed, 225, maxOffset, offset, angle))
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        particleList.forEach {
            paint.alpha = it.alpha
            canvas.drawCircle(it.x, it.y, it.radius, paint)
        }
    }


    private fun updateParticle(value: Float) {
        particleList.forEach {
            if (it.offset > it.maxOffset) {
                it.offset = 0f
                it.speed = random.nextInt(3) + 1.5f
                it.maxOffset = random.nextInt(250).toFloat()
            }
            it.alpha = ((1f - it.offset / it.maxOffset) * 0.8f * 225f).toInt()
            it.x = (mWidth / 2 + cos(it.angle) * (diffusionRadius + it.offset)).toFloat()
            if (it.y > mHeight / 2) {
                it.y = (sin(it.angle) * (diffusionRadius + it.offset) + mHeight / 2).toFloat()
            } else {
                it.y = (mHeight / 2 - sin(it.angle) * (diffusionRadius + it.offset)).toFloat()
            }
            it.offset += it.speed
        }
    }
}