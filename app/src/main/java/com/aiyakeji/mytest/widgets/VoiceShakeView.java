package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 音频波形显示
 */

public class VoiceShakeView extends View {
    private int colNums = 20;//柱子个数
    private float rate = 0.6f;//柱形总宽度所占控件总宽度的比例
    private float columnWidth;//柱形宽度
    private float columnHeight = 200;
    private float gap;//柱形之间间隔

    private float mWidth;
    private float mHeight;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int bottomLineColor = Color.GREEN;
    private LinearGradient mLinearGradient;


    public VoiceShakeView(Context context) {
        this(context, null);
    }

    public VoiceShakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceShakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mLinearGradient = new LinearGradient(0, 0, columnWidth, mHeight, Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        columnWidth = mWidth * rate / colNums;
        gap = mWidth * (1 - rate) / (colNums + 1);

        //画底部线
        mPaint.setColor(bottomLineColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);

        //画柱形
        mPaint.setShader(mLinearGradient);
        for (int i = 0; i < colNums; i++) {
            columnHeight = (float) (mHeight * Math.random());
            canvas.drawRect(gap + i * (columnWidth + gap), mHeight - columnHeight, gap + columnWidth + i * (columnWidth + gap), mHeight, mPaint);
        }

        postInvalidateDelayed(250);
    }
}
