package com.aiyakeji.mytest.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.aiyakeji.mytest.bean.HackerWord
import java.util.*

/**
 * @author CWQ
 * @date 2020/10/20
 */
class HackerView : View {

    private var mWidth = 0f
    private var mHeight = 0f
    private var wordSize = 80f
    private var wordOffset = 3f
    private val random = Random()
    private val allList = mutableListOf<List<HackerWord>>()
    private val animator = ValueAnimator.ofFloat(0f, 1f)
    private val paint = Paint()

    init {
        paint.color = Color.GREEN
        paint.isAntiAlias = true
        paint.textSize = wordSize

        animator.duration = 500
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updateWords()
            invalidate()
        }
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun updateWords() {
        allList.forEach { list ->
            list.forEach {
                if (it.baseY - it.speed > mHeight) {
                    it.baseY = 0f
                }
                it.baseY += it.speed
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()

        val horizontalNum = (mWidth / (wordSize + 100)).toInt()
        val verticalNum = (mHeight / (wordSize + wordOffset)).toInt()
        for (j in 0..horizontalNum) {
            val list = mutableListOf<HackerWord>()
            for (i in 0..verticalNum) {
                list.add(HackerWord(random.nextInt(3).toString(), j * (wordSize + 100), -i * (wordSize + wordOffset) - random.nextInt(200), 10f))
            }
            allList.add(list)
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        allList.forEach { list ->
            list.forEach { word ->
                canvas.drawText(word.word, word.baseX, word.baseY, paint)
            }
        }
    }
}