package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aiyakeji.mytest.R;

/**
 * 自定义控制圆盘
 */

public class ControlableCircleView extends View {

    private Context mContext;
    private float mWidth;
    private float mHeight;
    private float mClockRadio;//表针半径
    private float mBackRadio;//背景图片圆半径
    private Bitmap bg_bitmap;

    private float mLongClockLine = 40;//长表盘指针长度
    private int mClockNum = 120;//最大指针数量
    private float mPreAngle;//每个指针间偏移角度

    private Paint mClockPaint;//指针画笔
    private Paint mBgPaint;//背景图片画笔

    private int mPaintColor = Color.WHITE;
    private boolean mHasInit = false;
    private boolean mForbitRotate = false;//是否禁止旋转

    private float touchRotate = 0;
    private float downX;
    private float downY;
    private float movingX;
    private float movingY;

    public ControlableCircleView(Context context) {
        this(context, null);
    }

    public ControlableCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlableCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setClickable(true);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.ControlableCircleView);
        Drawable bg = attribute.getDrawable(R.styleable.ControlableCircleView_circle_bg);
        if (null != bg)
            bg_bitmap = drawableToBitmap(bg);
        attribute.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //长短指针所在的圆半径
        mClockRadio = Math.min(mWidth, mHeight) / 2 - mLongClockLine;
        //背景图片圆半径
        mBackRadio = mClockRadio - 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mHasInit) {
            mClockPaint = new Paint();
            mClockPaint.setAntiAlias(true);
            mClockPaint.setStrokeWidth(3);
            mClockPaint.setColor(mPaintColor);

            mPreAngle = 360f / mClockNum;
            mHasInit = true;
        }
        //画背景图片
        drawBg(canvas);
        canvas.rotate(touchRotate, mWidth / 2, mHeight / 2);
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
        if (null != bg_bitmap) {
            Bitmap squareBitmap;
            Bitmap scaledSrcBmp;
            int bgWidth = bg_bitmap.getWidth();
            int bgHeight = bg_bitmap.getHeight();
            int scaleWidth = 0, scaleHeight = 0;
            int x = 0, y = 0;
            if (bgWidth > bgHeight) {
                scaleWidth = scaleHeight = bgHeight;
                x = (bgWidth - bgHeight) / 2;
                y = 0;
                squareBitmap = Bitmap.createBitmap(bg_bitmap, x, y, scaleWidth, scaleHeight);
            } else if (bgWidth < bgHeight) {
                scaleWidth = scaleHeight = bgWidth;
                y = (bgHeight - bgWidth) / 2;
                x = 0;
                squareBitmap = Bitmap.createBitmap(bg_bitmap, x, y, scaleWidth, scaleHeight);
            } else {
                squareBitmap = bg_bitmap;
            }

            if (squareBitmap.getWidth() != mBackRadio * 2 || squareBitmap.getHeight() != mBackRadio * 2) {
                scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, (int) mBackRadio * 2,
                        (int) mBackRadio * 2, true);
            } else {
                scaledSrcBmp = squareBitmap;
            }

            Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                    scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bgCanvas = new Canvas(output);

            Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            bgCanvas.drawARGB(0, 0, 0, 0);
            bgCanvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            bgCanvas.drawBitmap(scaledSrcBmp, rect, rect, paint);

            //画圆形背景
            final Rect rectSrc = new Rect(0, 0, output.getWidth(), output.getHeight());
            final Rect rectDest = new Rect((int) (mWidth / 2 - mBackRadio), (int) (mHeight / 2 - mBackRadio),
                    (int) (mWidth / 2 + mBackRadio), (int) (mHeight / 2 + mBackRadio));
            mBgPaint = new Paint();
            canvas.drawBitmap(output, rectSrc, rectDest, mBgPaint);
        }
    }


    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
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
        return (float) ((Math.asin(sin1) + Math.asin(sin2)) / 2 * Math.PI * 360);
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
        if (!mForbitRotate)
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    movingX = event.getX();
                    movingY = event.getY();
                    //计算下落点、圆心、移动点三点间的夹角
                    float angle = calcAngle(downX, downY, mWidth / 2, mHeight / 2, movingX, movingY) / 18;
                    if (Math.abs(angle) >= mPreAngle) {
                        if (angle > 0) {
                            if (touchRotate / mPreAngle % 2 == 0)
                                touchRotate = mPreAngle;
                            else
                                touchRotate = 2 * mPreAngle;
                        } else {
                            if (touchRotate / mPreAngle % 2 == 0)
                                touchRotate = -mPreAngle;
                            else
                                touchRotate = -2 * mPreAngle;
                        }
                        //旋转监听
                        if (null != mOnRotateListener) {
                            if (angle > 0 && movingY < mHeight / 2 || angle < 0 && movingY > mHeight / 2)
                                mOnRotateListener.onRotateRight(1);
                            else
                                mOnRotateListener.onRotateLeft(1);
                        }

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
        if (!mForbitRotate) {
            if (touchRotate / mPreAngle % 2 == 0)
                touchRotate = -mPreAngle;
            else
                touchRotate = -2 * mPreAngle;
            if (null != mOnRotateListener)
                mOnRotateListener.onRotateLeft(1);
            invalidate();
        }
    }

    /**
     * 向右旋转一格
     */
    public void rotateRight() {
        if (!mForbitRotate) {
            if (touchRotate / mPreAngle % 2 == 0)
                touchRotate = mPreAngle;
            else
                touchRotate = 2 * mPreAngle;
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
        mForbitRotate = forbitRotate;
    }


    /**
     * 获取控件是否禁止了旋转
     *
     * @return
     */
    public boolean getForbitRotate() {
        return mForbitRotate;
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


    public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
}
