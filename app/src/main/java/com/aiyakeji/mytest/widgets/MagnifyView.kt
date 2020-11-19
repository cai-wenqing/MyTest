package com.aiyakeji.mytest.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.BitmapUtils

/**
 * @author CWQ
 * @date 11/18/20
 */
class MagnifyView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0

    //放大倍数
    private val factor = 2

    //放大区域半径
    private val radius = 90f
    private val glassWidth = 6f
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var glassCenterX = 0f
    private var glassCenterY = 0f

    private var mMatrix: Matrix = Matrix()
    private var bgBitmap: Bitmap? = null
    private var magnifierDrawable: ShapeDrawable? = null


    init {
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = glassWidth
        mPaint.style = Paint.Style.STROKE
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        if (mWidth > 0 && mHeight > 0) {
            initBitmap()
        }
    }

    private fun initBitmap() {
        if (bgBitmap == null) {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, R.mipmap.princess, options)
            options.inJustDecodeBounds = false
            val sampleSize = BitmapUtils.calculateInSampleSize(options, mWidth, mHeight)
            options.inSampleSize = sampleSize
            val bitmapSource = BitmapFactory.decodeResource(resources, R.mipmap.princess, options)

            bgBitmap = BitmapUtils.scaleFitBitmap(bitmapSource, mWidth, mHeight)


            glassCenterX = mWidth / 2f
            glassCenterY = mHeight / 2f
            val shader = BitmapShader(Bitmap.createScaledBitmap(bgBitmap, bgBitmap!!.width * factor, bgBitmap!!.height * factor, true),
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            magnifierDrawable = ShapeDrawable(OvalShape())
            magnifierDrawable?.paint?.shader = shader
            mMatrix.setTranslate(radius - glassCenterX * factor, radius - glassCenterY * factor)
            magnifierDrawable?.paint?.shader?.setLocalMatrix(mMatrix)
            magnifierDrawable?.setBounds(0, 0, (radius * 2).toInt(), (radius * 2).toInt())
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bgBitmap, 0f, 0f, null)
        drawGlass(canvas)
        magnifierDrawable?.draw(canvas)
    }

    /**
     * 画放大镜框
     * @param canvas Canvas
     */
    private fun drawGlass(canvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(glassCenterX, glassCenterY, radius + glassWidth / 2, mPaint)
        mPaint.style = Paint.Style.FILL
        canvas.drawRoundRect(glassCenterX - 15, glassCenterY + radius, glassCenterX + 15, glassCenterY + radius + 90, 10f, 10f, mPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        glassCenterX = x
        glassCenterY = y - 200
        mMatrix.setTranslate(radius - glassCenterX * factor, radius - glassCenterY * factor)
        magnifierDrawable?.paint?.shader?.setLocalMatrix(mMatrix)
        magnifierDrawable?.setBounds((x - radius).toInt(), (glassCenterY - radius).toInt(), (x + radius).toInt(), (glassCenterY + radius).toInt())

        invalidate()
        return true
    }
}