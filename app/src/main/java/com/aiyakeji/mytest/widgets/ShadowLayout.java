package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.aiyakeji.mytest.R;

/**
 * Author：CWQ
 * Date：2019/7/4
 * Desc：阴影布局
 */
public class ShadowLayout extends FrameLayout {

    private final int FLAG_SIDES_TOP = 0x001;
    private final int FLAG_SIDES_RIGHT = 0x002;
    private final int FLAG_SIDES_BOTTOM = 0x004;
    private final int FLAG_SIDES_LEFT = 0x008;
    private final int FLAG_SIDES_ALL = 0x00f;

    private int mShadowColor = Color.BLACK;
    private float mShadowRadius = 0f;
    private float mDx = 0f;
    private float mDy = 0f;
    private float mCornerRadius = 0f;
    private int mBorderColor = Color.BLACK;
    private float mBorderWidth = 0f;
    private int mShadowSide = 15;

    //图层合成模式
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private PorterDuffXfermode xfermode;
    private RectF mContentRF;
    private RectF mBorderRF;


    public ShadowLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
            mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_sl_shadowColor, Color.BLACK);
            mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_sl_shadowRadius, 0f);
            mDx = typedArray.getDimension(R.styleable.ShadowLayout_sl_dx, 0f);
            mDy = typedArray.getDimension(R.styleable.ShadowLayout_sl_dy, 0f);
            mCornerRadius = typedArray.getDimension(R.styleable.ShadowLayout_sl_cornerRadius, 0);
            mBorderColor = typedArray.getColor(R.styleable.ShadowLayout_sl_borderColor, Color.RED);
            mBorderWidth = typedArray.getDimension(R.styleable.ShadowLayout_sl_borderWidth, 0f);
            mShadowSide = typedArray.getInt(R.styleable.ShadowLayout_sl_shadowSides, FLAG_SIDES_ALL);
            typedArray.recycle();
        }

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        resetPaint();

        int xPadding = (int) (mShadowRadius + Math.abs(mDx));
        int yPadding = (int) (mShadowRadius + Math.abs(mDy));

        setPadding(
                (mShadowSide | FLAG_SIDES_LEFT) == mShadowSide ? xPadding : 0,
                (mShadowSide | FLAG_SIDES_TOP) == mShadowSide ? yPadding : 0,
                (mShadowSide | FLAG_SIDES_RIGHT) == mShadowSide ? xPadding : 0,
                (mShadowSide | FLAG_SIDES_BOTTOM) == mShadowSide ? yPadding : 0
        );
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentRF = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
        float innerWidth = mBorderWidth / 3;
        if (innerWidth > 0) {
            mBorderRF = new RectF(mContentRF.left + innerWidth, mContentRF.top + innerWidth, mContentRF.right - innerWidth, mContentRF.bottom - innerWidth);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawShadow(canvas);
        drawChild(canvas);
        drawBorder(canvas);
    }


    /**
     * 绘制阴影
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {
        canvas.save();
        mPaint.setShadowLayer(mShadowRadius, mDx, mDy, mShadowColor);
        canvas.drawRoundRect(mContentRF, mCornerRadius, mCornerRadius, mPaint);
        resetPaint();
        canvas.restore();
    }

    /**
     * 绘制子view
     *
     * @param canvas
     */
    private void drawChild(Canvas canvas) {
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.addRect(mContentRF, Path.Direction.CW);
        mPath.addRoundRect(mContentRF, mCornerRadius, mCornerRadius, Path.Direction.CW);
        mPaint.setXfermode(xfermode);
        canvas.drawPath(mPath, mPaint);
        resetPaint();
        mPath.reset();

        canvas.restore();
    }

    /**
     * 绘制边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        if (mBorderRF != null) {
            canvas.save();
            mPaint.setStrokeWidth(mBorderWidth);
            mPaint.setColor(mBorderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(mBorderRF, mCornerRadius, mCornerRadius, mPaint);
            resetPaint();
            canvas.restore();
        }
    }


    /**
     * 重置画笔属性
     */
    private void resetPaint() {
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
    }
}
