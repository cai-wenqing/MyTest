package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.aiyakeji.mytest.R;

import java.lang.ref.WeakReference;

/**
 * @author CWQ
 * @date 2020-02-11
 */
public class RewardProgress extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_LIGHT_OFFSET = 3;
    private static final int DEFAULT_PROGRESS_BG_OFFSET = 2;
    private static final int DEFAULT_PROGRESS_HEIGHT = 30;
    private static final int DEFAULT_BG_COLOR = 0XFF0000FF;

    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;

    private int mRealWidth;
    private int mRealHeight;

    /**
     * 进度条高度
     */
    private int mProgressHeight = dp2px(DEFAULT_PROGRESS_HEIGHT);
    /**
     * 背景外泛光宽度
     */
    private int lightExtra = dp2px(DEFAULT_LIGHT_OFFSET);
    /**
     * 进度条和背景间隔距离
     */
    private int progressBgOffset = dp2px(DEFAULT_PROGRESS_BG_OFFSET);

    /**
     * 图片上截图范围
     */
    private Rect srcRect = new Rect();
    /**
     * 图片绘制在屏幕上范围
     */
    private RectF dstRect = new RectF();
    private RectF bgRect = new RectF();
    private Rect mTextBound = new Rect();
    private Path mPath = new Path();
    private Bitmap mProgressBitmap;


    public RewardProgress(Context context) {
        this(context, null);
    }

    public RewardProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RewardProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mRealHeight = height;
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 测量高度
     *
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //文字高度
            float textHeight = mTextPaint.descent() - mTextPaint.ascent();

            result = (int) (getPaddingTop() + getPaddingBottom()
                    + lightExtra * 2 + progressBgOffset * 2
                    + Math.max(mProgressHeight, Math.abs(textHeight)));
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        mBgPaint.setColor(DEFAULT_BG_COLOR);
        bgRect.set(lightExtra, lightExtra, mRealWidth - lightExtra, mRealHeight - lightExtra);
        canvas.drawRoundRect(bgRect, mProgressHeight / 2, mProgressHeight / 2, mBgPaint);

        //进度条
        mBgPaint.reset();
        mBgPaint.setAntiAlias(true);
        if (mProgressBitmap == null) {
            mProgressBitmap = getProgressBitmap();
        }
        float radio = getProgress() * 1.0f / getMax();
        srcRect.set(0, 0, (int) ((mRealWidth - lightExtra - progressBgOffset) * radio), mProgressBitmap.getHeight());
        dstRect.set(lightExtra + progressBgOffset, mRealHeight / 2 - mProgressBitmap.getHeight() / 2, srcRect.width(), mRealHeight / 2 + mProgressBitmap.getHeight() / 2);
        mPath.reset();
        mPath.addRoundRect(dstRect, mProgressHeight / 2, mProgressHeight / 2, Path.Direction.CW);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mProgressBitmap, srcRect, dstRect, mBgPaint);

        //文字
        String text = getProgress() + "元";
        mTextPaint.getTextBounds(text,0,text.length(),mTextBound);
        if (mTextBound.width() < dstRect.width()){
            canvas.drawText(text, dstRect.left + dstRect.width() / 2 - mTextBound.width() / 2, mRealHeight / 2 + mTextBound.height() / 2, mTextPaint);
        }
    }


    private Bitmap getProgressBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = getContext().getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.mipmap.rewrad_progress, options);
        options.inSampleSize = options.outHeight / mProgressHeight;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, R.mipmap.rewrad_progress, options);
    }


    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }


    private MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        private final WeakReference<RewardProgress> mProgress;

        public MyHandler(RewardProgress rewardProgress) {
            mProgress = new WeakReference<>(rewardProgress);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProgress.get() == null) {
                return;
            }
            int value = msg.what;
            int progress = mProgress.get().getProgress();
            if (progress < value) {
                mProgress.get().setProgress(++progress);
                sendEmptyMessageDelayed(value, 10);
            }
        }
    }


    public void setProgressWithAnim(int progress) {
        myHandler.sendEmptyMessage(progress);
    }
}
