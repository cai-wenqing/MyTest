package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by track on 2017/10/12.
 * 裁剪圆形
 */

public class CropView extends View {
    public CropView(Context context) {
        super(context);
        init();
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    int opadding = dip2px(5);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(0xaf000000);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        paint.setAntiAlias(true);
        paint.setColor(0x00000000);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 - opadding, paint);
    }

    public int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
