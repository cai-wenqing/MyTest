package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * 手势锁view
 */

public class GestureLockView extends View {

    private GestureLockPoint[][] mPoints = new GestureLockPoint[3][3];//点集合
    private ArrayList<GestureLockPoint> selectedPoints = new ArrayList<>();
    private GestureLockPoint startPoint;//跟随直线起点圆圈

    private Paint normalPaint;
    private Paint pressedPaint;
    private Paint arrowPaint;
    private Paint errorPaint;

    private boolean isInit = false;

    private float mWidth;
    private float mHeight;

    private float preSideLong;//画图部分正方形边长

    private float offsetX = 0;//x轴上的偏移量
    private float offsetY = 0;//y轴上的偏移量
    private float slideSize;//大圆半径

    private float movingX;
    private float movingY;

    public interface onFinishListener {
        void onFinish(String password);
    }

    private onFinishListener mOnFinishListener;

    public void setOnFinishListener(onFinishListener mOnFinishListener) {
        this.mOnFinishListener = mOnFinishListener;
    }

    public GestureLockView(Context context) {
        this(context, null);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        preSideLong = Math.min(mWidth, mHeight);
        if (mWidth > preSideLong) {
            offsetX = (mWidth - preSideLong) / 2;
        } else if (mHeight > preSideLong) {
            offsetY = (mHeight - preSideLong) / 2;
        }
        slideSize = preSideLong / 12;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化数据与paint
        if (!isInit) {
            for (int i = 0; i < mPoints.length; i++) {
                for (int j = 0; j < mPoints.length; j++) {
                    mPoints[i][j] = new GestureLockPoint(offsetX + slideSize + slideSize + 4 * slideSize * j, offsetY + slideSize + slideSize + 4 * slideSize * i, i * mPoints.length + j);
                }
            }

            normalPaint = new Paint();
            normalPaint.setColor(Color.GRAY);
            normalPaint.setAntiAlias(true);
            normalPaint.setStyle(Paint.Style.STROKE);
            normalPaint.setStrokeWidth(3);

            pressedPaint = new Paint();
            pressedPaint.setColor(Color.GREEN);
            pressedPaint.setAntiAlias(true);
            pressedPaint.setStyle(Paint.Style.STROKE);
            pressedPaint.setStrokeWidth(3);

            arrowPaint = new Paint();
            arrowPaint.setColor(Color.GREEN);
            arrowPaint.setStyle(Paint.Style.FILL);
            arrowPaint.setAntiAlias(true);

            errorPaint = new Paint();
            errorPaint.setColor(Color.RED);
            errorPaint.setAntiAlias(true);
            errorPaint.setStyle(Paint.Style.STROKE);
            errorPaint.setStrokeWidth(3);

            isInit = true;
        }


        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints.length; j++) {
                //画圆圈
                if (mPoints[i][j].isSelected()) {
                    canvas.drawCircle(mPoints[i][j].centerX, mPoints[i][j].centerY, slideSize, pressedPaint);
                    canvas.drawCircle(mPoints[i][j].centerX, mPoints[i][j].centerY, slideSize / 3, pressedPaint);
                } else {
                    canvas.drawCircle(mPoints[i][j].centerX, mPoints[i][j].centerY, slideSize, normalPaint);
                    canvas.drawCircle(mPoints[i][j].centerX, mPoints[i][j].centerY, slideSize / 3, normalPaint);
                }
            }
        }

        //画连接圆圈的直线
        if (selectedPoints.size() > 0) {
            for (int i1 = 0; i1 < selectedPoints.size() - 1; i1++) {
                float disPoint = calcDisBetweenPoint(selectedPoints.get(i1).centerX, selectedPoints.get(i1).centerY, selectedPoints.get(i1 + 1).centerX, selectedPoints.get(i1 + 1).centerY);
                float cos1 = (selectedPoints.get(i1 + 1).centerX - selectedPoints.get(i1).centerX) / disPoint;
                float sin1 = (selectedPoints.get(i1 + 1).centerY - selectedPoints.get(i1).centerY) / disPoint;
                float x1 = selectedPoints.get(i1).centerX + slideSize * cos1;
                float y1 = selectedPoints.get(i1).centerY + slideSize * sin1;

                float x2 = selectedPoints.get(i1 + 1).centerX - slideSize * cos1;
                float y2 = selectedPoints.get(i1 + 1).centerY - slideSize * sin1;
                //连接线
                canvas.drawLine(x1, y1, x2, y2, pressedPaint);
                //箭头
                drawArrow(canvas, arrowPaint, selectedPoints.get(i1), selectedPoints.get(i1 + 1), slideSize / 3, 30);
            }
        }

        //跟随直线
        if (null != startPoint) {
            float dis = calcDisBetweenPoint(startPoint.centerX, startPoint.centerY, movingX, movingY);
            if (dis > slideSize) {
                float cos1 = (movingX - startPoint.centerX) / dis;
                float sin1 = (movingY - startPoint.centerY) / dis;
                float startX = startPoint.centerX + slideSize * cos1;
                float startY = startPoint.centerY + slideSize * sin1;
                canvas.drawLine(startX, startY, movingX, movingY, pressedPaint);
            }
        }
    }


    /**
     * 画箭头
     * @param canvas
     * @param paint
     * @param start 起始圆
     * @param end 箭头所指圆
     * @param arrowHeight 箭头尖距底部垂直距离
     * @param angle 箭头单侧角度
     */
    private void drawArrow(Canvas canvas, Paint paint, GestureLockPoint start, GestureLockPoint end, Float arrowHeight, int angle) {
        float d = calcDisBetweenPoint(start.centerX, start.centerY, end.centerX, end.centerY);
        float sin_B = ((end.centerX - start.centerX) / d);
        float cos_B = ((end.centerY - start.centerY) / d);
        float tan_A = (float) Math.tan(Math.toRadians(angle));
        float h = (float) (d - arrowHeight - slideSize);
        float l = arrowHeight * tan_A;
        float a = l * sin_B;
        float b = l * cos_B;
        float x0 = h * sin_B;
        float y0 = h * cos_B;
        float x1 = start.centerX + (h + arrowHeight) * sin_B;
        float y1 = start.centerY + (h + arrowHeight) * cos_B;
        float x2 = start.centerX + x0 - b;
        float y2 = start.centerY + y0 + a;
        float x3 = start.centerX + x0 + b;
        float y3 = start.centerY + y0 - a;
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        movingX = event.getX();
        movingY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectedPoints.clear();
                startPoint = null;
                for (int i = 0; i < mPoints.length; i++) {
                    for (int j = 0; j < mPoints.length; j++) {
                        if (calcDisBetweenPoint(mPoints[i][j].centerX, mPoints[i][j].centerY, movingX, movingY) <= slideSize) {
                            mPoints[i][j].setSelected(true);
                            selectedPoints.add(mPoints[i][j]);
                            startPoint = mPoints[i][j];
                        } else {
                            mPoints[i][j].setSelected(false);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mPoints.length; i++) {
                    for (int j = 0; j < mPoints.length; j++) {
                        if (calcDisBetweenPoint(mPoints[i][j].centerX, mPoints[i][j].centerY, movingX, movingY) <= slideSize) {
                            mPoints[i][j].setSelected(true);
                            if (!selectedPoints.contains(mPoints[i][j])) {
                                selectedPoints.add(mPoints[i][j]);
                                startPoint = mPoints[i][j];
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startPoint = null;
                if (null != mOnFinishListener) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < selectedPoints.size(); i++) {
                        sb.append("" + selectedPoints.get(i).flag);
                    }
                    mOnFinishListener.onFinish(sb.toString());
                }
                break;
        }

        invalidate();
        return super.onTouchEvent(event);
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

    /**
     * 重置
     */
    public void reset() {
        startPoint = null;
        selectedPoints.clear();
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints.length; j++) {
                if (mPoints[i][j].isSelected())
                    mPoints[i][j].setSelected(false);
            }
        }
        invalidate();
    }
}
