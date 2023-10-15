package com.aiyakeji.mytest.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.BitmapUtils;

/**
 * 自定义刻度盘
 */

public class ScalePanelView extends View {
    private final String TAG = "ControllerCircle";
    private float mWidth;
    private float mHeight;
    private float mClockRadio;//表针半径
    private float mCircleBitmapRadio;//背景图片圆半径
    private Bitmap bgBitmap;

    private final float mLongClockLine = 40;//长表盘指针长度
    private final int mClockNum = 120;//最大指针数量
    private final float mPreAngle;//每个指针间偏移角度

    private final Paint mClockPaint = new Paint();//指针画笔
    private final Paint mBgPaint = new Paint();//背景图片画笔

    private BitmapShader bitmapShader;
    private final Matrix mMatrix = new Matrix();
    private int mPaintColor = Color.WHITE;
    private boolean mForbidRotate = false;//是否禁止旋转
    private float mStartAngle;
    private float mRotationAngle;
    private float mRawRotationAngle;

    public ScalePanelView(Context context) {
        this(context, null);
    }

    public ScalePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        initAttrs(attrs);

        mBgPaint.setAntiAlias(true);

        mClockPaint.setAntiAlias(true);
        mClockPaint.setStrokeWidth(3);
        mClockPaint.setColor(mPaintColor);
        mPreAngle = 360f / mClockNum;
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.ControlableCircleView);
        Drawable bgDrawable = attribute.getDrawable(R.styleable.ControlableCircleView_circle_bg);
        attribute.recycle();
        bgBitmap = BitmapUtils.getBitmapFromDrawable(bgDrawable);
        if (bgBitmap != null) {
            bitmapShader = new BitmapShader(bgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //长短指针所在的圆半径
        mClockRadio = Math.min(mWidth, mHeight) / 2 - mLongClockLine;
        //背景图片圆半径
        mCircleBitmapRadio = mClockRadio - 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(mRotationAngle, mWidth / 2, mHeight / 2);
        drawBg(canvas);
        //画长短指针
        for (int i = 0; i < mClockNum; i++) {
            if (i % 2 == 0)
                canvas.drawLine(mWidth / 2, mHeight / 2 - mClockRadio, mWidth / 2, mHeight / 2 - mClockRadio - mLongClockLine, mClockPaint);
            else
                canvas.drawLine(mWidth / 2, mHeight / 2 - mClockRadio, mWidth / 2, mHeight / 2 - mClockRadio - mLongClockLine / 2, mClockPaint);
            canvas.rotate(mPreAngle, mWidth / 2, mHeight / 2);
        }
    }

    /**
     * 画背景图片
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        if (bgBitmap != null && bitmapShader != null) {
            canvas.save();

            int bSize = Math.min(bgBitmap.getWidth(), bgBitmap.getHeight());
            float scale = mCircleBitmapRadio * 2 / bSize;
            mMatrix.setScale(scale, scale);
            bitmapShader.setLocalMatrix(mMatrix);
            mBgPaint.setShader(bitmapShader);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mCircleBitmapRadio, mBgPaint);

            canvas.restore();
        }
    }


    private float getAngleForPoint(float p1X, float p1Y) {
        double tx = p1X - mWidth / 2, ty = p1Y - mHeight / 2;
        double length = Math.sqrt(tx * tx + ty * ty);
        double r = Math.acos(ty / length);

        float angle = (float) Math.toDegrees(r);

        if (p1X > mWidth / 2)
            angle = 360f - angle;

        angle = angle + 90f;

        if (angle > 360f)
            angle = angle - 360f;

        return angle;
    }


    private float getNormalizedAngle(float angle) {
        while (angle < 0.f)
            angle += 360.f;

        return angle % 360.f;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mForbidRotate)
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartAngle = getAngleForPoint(event.getX(), event.getY()) - mRawRotationAngle;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float angle = getAngleForPoint(event.getX(), event.getY());
                    mRawRotationAngle = angle - mStartAngle;
                    mRotationAngle = getNormalizedAngle(mRawRotationAngle);
//                    Log.d(TAG, "onTouchEvent: angle:" + angle + ",mRawRotationAngle:" + mRawRotationAngle + ",mRotationAngle:" + mRotationAngle);
                    if (mOnRotateListener != null){
                        mOnRotateListener.onRotate(mRotationAngle);
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }

        return super.onTouchEvent(event);
    }


    /**
     * 设置是否禁止旋转
     *
     * @param forbidRotate
     */
    public void setForbidRotate(boolean forbidRotate) {
        mForbidRotate = forbidRotate;
    }


    /**
     * 获取控件是否禁止了旋转
     *
     * @return
     */
    public boolean getForbidRotate() {
        return mForbidRotate;
    }


    //设置滚动监听
    private onRotateListener mOnRotateListener;

    public interface onRotateListener {
        void onRotate(float angle);
    }

    public void setOnRotate(onRotateListener onRotateListener) {
        mOnRotateListener = onRotateListener;
    }
}
