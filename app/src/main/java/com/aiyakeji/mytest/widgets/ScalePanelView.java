package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aiyakeji.mytest.R;

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

    private float mLongClockLine = 40;//长表盘指针长度
    private int mClockNum = 120;//最大指针数量
    private float mPreAngle;//每个指针间偏移角度

    private Paint mClockPaint = new Paint();//指针画笔
    private Paint mBgPaint = new Paint();//背景图片画笔

    private BitmapShader bitmapShader;
    private Matrix mMatrix;
    private int mPaintColor = Color.WHITE;
    private boolean mForbidRotate = false;//是否禁止旋转

    private float touchRotate = 0;
    private float downX;
    private float downY;
    private float movingX;
    private float movingY;

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

        mMatrix = new Matrix();
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
        bgBitmap = getBitmapFromDrawable(bgDrawable);
        if (bgBitmap != null) {
            bitmapShader = new BitmapShader(bgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure: width:" + mWidth + ",height:" + mHeight);
        //长短指针所在的圆半径
        mClockRadio = Math.min(mWidth, mHeight) / 2 - mLongClockLine;
        //背景图片圆半径
        mCircleBitmapRadio = mClockRadio - 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(touchRotate, mWidth / 2, mHeight / 2);
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


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 计算三个点的夹角
     *
     * @param p1X
     * @param p1Y
     * @param centerX
     * @param centerY
     * @param p2X
     * @param p2Y
     * @return
     */
    private float calcAngle(float p1X, float p1Y, float centerX, float centerY, float p2X, float p2Y) {
        float dis1 = calcDisBetweenPoint(p1X, p1Y, centerX, centerY);
        float sin1 = (centerX - p1X) / dis1;
        float dis2 = calcDisBetweenPoint(centerX, centerY, p2X, p2Y);
        float sin2 = (p2X - centerX) / dis2;
        return (float) ((Math.asin(sin1) + Math.asin(sin2)) / Math.PI * 180);
    }

    /**
     * 计算两个点中间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private float calcDisBetweenPoint(float x1, float y1, float x2, float y2) {
        float disX = Math.abs(x1 - x2);
        float disY = Math.abs(y1 - y2);
        return (float) Math.sqrt(disX * disX + disY * disY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mForbidRotate)
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    movingX = event.getX();
                    movingY = event.getY();
                    //计算下落点、圆心、移动点三点间的夹角
                    float angle = calcAngle(downX, downY, mWidth / 2, mHeight / 2, movingX, movingY);
                    if (movingY > mHeight / 2) {
                        angle = -angle;
                    }
//                    Log.d(TAG, "onTouchEvent: angle:" + angle + ",downX:" + downX + ",downY:" + downY + ",movingX:" + movingX + ",movingY:" + movingY);
                    if (Math.abs(angle) >= mPreAngle) {
                        if (angle > 0) {
                            angle = mPreAngle;
                            if (mOnRotateListener != null) {
                                mOnRotateListener.onRotateRight(1);
                            }
                        } else {
                            angle = -mPreAngle;
                            if (mOnRotateListener != null) {
                                mOnRotateListener.onRotateLeft(1);
                            }
                        }

                        touchRotate += angle;

                        downX = movingX;
                        downY = movingY;
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }

        return super.onTouchEvent(event);
    }


    /**
     * 向左旋转一格
     */
    public void rotateLeft() {
        if (!mForbidRotate) {
            touchRotate -= mPreAngle;
            if (null != mOnRotateListener)
                mOnRotateListener.onRotateLeft(1);
            invalidate();
        }
    }

    /**
     * 向右旋转一格
     */
    public void rotateRight() {
        if (!mForbidRotate) {
            touchRotate += mPreAngle;
            if (null != mOnRotateListener)
                mOnRotateListener.onRotateRight(1);
            invalidate();
        }
    }


    /**
     * 设置是否禁止旋转
     *
     * @param forbitRotate
     */
    public void setForbitRotate(boolean forbitRotate) {
        mForbidRotate = forbitRotate;
    }


    /**
     * 获取控件是否禁止了旋转
     *
     * @return
     */
    public boolean getForbitRotate() {
        return mForbidRotate;
    }


    //设置滚动监听
    private onRotateListener mOnRotateListener;

    public interface onRotateListener {
        void onRotateLeft(int num);

        void onRotateRight(int num);
    }

    public void setOnRotate(onRotateListener onRotateListener) {
        mOnRotateListener = onRotateListener;
    }
}
