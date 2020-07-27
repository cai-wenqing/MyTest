package com.aiyakeji.mytest.widgets.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

/**
 * @author caiwenqing
 * @data 2018/5/15
 * description:自定义日历
 */
public class CalendarOrderView extends View {
    private static final String TAG = "CalendarView测试";

    private static final int WEEK_LENGTH = 7;
    private float mWidth;
    private float mHeight;
    private float mDayWidth;
    private float mDayHeight;

    //标题
    private Paint mTitlePaint;
    //周
    private Paint mWeekPaint;
    //日期
    private Paint mDayPaint;
    //价格
    private Paint mPricePaint;

    private int mDisableDayColor = Color.GRAY;
    private int mEnableDayColor = Color.BLACK;
    private int mEnablePriceColor = Color.RED;
    private int mSelectColor = Color.WHITE;

    private float mTitleHeight = 130;
    private float mWeekHeight = 90;

    private String currentDate;
    //某月的天数
    private int daysInMonth;

    //该月第一天是周几（0为周日）
    private int firstDayWeekIndex;
    //行数
    private int row;

    private DayModel[][] mDayList;
    //可选择的日期
    private HashMap<Integer, Float> mEnableDays = new HashMap<>();

    private HashMap<Integer, Float> mSelectedDays = new HashMap<>();

    //是否需要重绘
    private boolean needDraw = false;

    public CalendarOrderView(Context context) {
        super(context);
        init();
    }

    public CalendarOrderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarOrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setColor(Color.BLACK);
        mTitlePaint.setTextSize(50);

        mWeekPaint = new Paint();
        mWeekPaint.setAntiAlias(true);
        mWeekPaint.setTextSize(30);
        mWeekPaint.setColor(Color.BLACK);

        mDayPaint = new Paint();
        mDayPaint.setAntiAlias(true);
        mDayPaint.setTextSize(30);
        mDayPaint.setColor(mDisableDayColor);

        mPricePaint = new Paint();
        mPricePaint.setAntiAlias(true);
        mPricePaint.setTextSize(25);
        mPricePaint.setColor(mEnablePriceColor);

        mEnableDays.put(17, 2f);
        mEnableDays.put(19, 33.5f);
        mEnableDays.put(20, 22.2f);
        mSelectedDays.put(17, 2f);

        currentDate = "2018-05-17";
        daysInMonth = DateTimeUtil.getDaysFromYearMonth(currentDate);

        String[] dateSplit = currentDate.split("-");
        String firstDay = dateSplit[0] + "-" + dateSplit[1] + "-01";
        firstDayWeekIndex = DateTimeUtil.formatDateGetWeekindex(firstDay);

        //补齐前面的天数，用来计算日期数组大小和绘图开始位置
        int tempDaysInMonth = daysInMonth + firstDayWeekIndex;

        //计算行数
        row = tempDaysInMonth / WEEK_LENGTH + (tempDaysInMonth % WEEK_LENGTH == 0 ? 0 : 1);

        mDayList = new DayModel[row][];
        mDayList[0] = new DayModel[WEEK_LENGTH - firstDayWeekIndex];

        //初始化日期数组大小
        if (tempDaysInMonth % WEEK_LENGTH == 0) {
            for (int i = 1; i < row; i++) {
                mDayList[i] = new DayModel[WEEK_LENGTH];
            }
        } else {
            for (int i = 1; i < row - 1; i++) {
                mDayList[i] = new DayModel[WEEK_LENGTH];
            }
            mDayList[row - 1] = new DayModel[tempDaysInMonth % WEEK_LENGTH];
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mDayWidth = mWidth / WEEK_LENGTH;
        mDayHeight = (mHeight - mTitleHeight - mWeekHeight) / row;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initDayList();

        drawTitle(canvas);

        drawWeek(canvas);

        drawDays(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (y > (mTitleHeight + mWeekHeight + mDayHeight)) {
                    float dis = y - mTitleHeight - mWeekHeight;
                    int dayRow, dayCol;
                    if (dis % mDayHeight != 0) {
                        dayRow = (int) Math.floor(dis / mDayHeight);
                    } else {
                        dayRow = (int) (dis / mDayHeight) - 1;
                    }

                    if (x % mDayWidth != 0) {
                        dayCol = (int) Math.floor(x / mDayWidth);
                    } else {
                        dayCol = (int) (x / mDayWidth) == 0 ? 0 : ((int) (x / mDayWidth) - 1);
                    }
                    int day = mDayList[dayRow][dayCol].getDay();
                    if (mEnableDays.containsKey(day)) {
                        mSelectedDays.clear();
                        mSelectedDays.put(day, 33.33f);
                        needDraw = true;
                    }
                } else if (y > (mTitleHeight + mWeekHeight) &&
                        y < (mTitleHeight + mWeekHeight + mDayHeight) &&
                        x > (firstDayWeekIndex * mDayWidth)) {
                    int dayCol;
                    float dis = x - firstDayWeekIndex * mDayWidth;
                    if (dis % mDayWidth != 0) {
                        dayCol = (int) Math.floor(dis / mDayWidth);
                    } else {
                        dayCol = (int) (dis / mDayWidth) - 1;
                    }
                    int day = mDayList[0][dayCol].getDay();
                    if (mEnableDays.containsKey(day)) {
                        mSelectedDays.clear();
                        mSelectedDays.put(day, 33.33f);
                        needDraw = true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (needDraw) {
                    invalidate();
                    needDraw = false;
                }
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 画标题
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, mWidth, mTitleHeight, bgPaint);

        String[] dateSplit = currentDate.split("-");
        String title = dateSplit[0] + "年" + dateSplit[1] + "月";

        Rect textBound = new Rect();
        mTitlePaint.getTextBounds(title, 0, title.length(), textBound);
        float textStart = (mWidth - textBound.width()) / 2;
        Paint.FontMetrics fontMetrics = mTitlePaint.getFontMetrics();
        float baseLine = mTitleHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(title, textStart, baseLine, mTitlePaint);
    }

    /**
     * 画周
     *
     * @param canvas
     */
    private void drawWeek(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.GREEN);
        canvas.drawRect(0, mTitleHeight, mWidth, mTitleHeight + mWeekHeight, bgPaint);

        String[] weeks = new String[]{"日", "一", "二", "三", "四", "五", "六"};
        float width = mWidth / WEEK_LENGTH;
        for (int i = 0; i < weeks.length; i++) {
            Rect textBound = new Rect();
            mWeekPaint.getTextBounds(weeks[i], 0, weeks[i].length(), textBound);
            float textStart = width * i + (width - textBound.width()) / 2;
            Paint.FontMetrics fontMetrics = mWeekPaint.getFontMetrics();
            float baseLine = mTitleHeight + mWeekHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
            canvas.drawText(weeks[i], textStart, baseLine, mWeekPaint);
        }
    }

    /**
     * 画天
     *
     * @param canvas
     */
    private void drawDays(Canvas canvas) {
        for (int i = 0; i < mDayList.length; i++) {
            for (int j = 0; j < mDayList[i].length; j++) {
                if (mDayList[i][j] != null) {
                    DayModel model = mDayList[i][j];
                    String day = model.getDay() + "";
                    String price = "¥" + model.getPrice();
                    if (model.getDayState() == DayState.STATE_DISABLE) {
                        //不可选状态
                        //画日期
                        mDayPaint.setColor(mDisableDayColor);
                        Rect dayTextBound = new Rect();
                        mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                        float textStart = model.getCenterX() - dayTextBound.width() / 2;
                        Paint.FontMetrics fontMetrics = mWeekPaint.getFontMetrics();
                        float baseLine = model.getCenterY() - model.getHeight() / 4 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                        canvas.drawText(day, textStart, baseLine, mDayPaint);
                    } else if (model.getDayState() == DayState.STATE_ENABLE) {
                        //可选状态
                        //画价格
                        mPricePaint.setColor(mEnablePriceColor);
                        Rect priceTextBound = new Rect();
                        mPricePaint.getTextBounds(price, 0, price.length(), priceTextBound);
                        float priceStart = model.getCenterX() - priceTextBound.width() / 2;
                        Paint.FontMetrics priceMetrics = mWeekPaint.getFontMetrics();
                        float priceLine = model.getCenterY() + model.getHeight() / 4 + (Math.abs(priceMetrics.ascent) - priceMetrics.descent) / 2;
                        canvas.drawText(price, priceStart, priceLine, mPricePaint);

                        //画日期
                        mDayPaint.setColor(mEnableDayColor);
                        Rect dayTextBound = new Rect();
                        mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                        float textStart = model.getCenterX() - dayTextBound.width() / 2;
                        Paint.FontMetrics fontMetrics = mWeekPaint.getFontMetrics();
                        float baseLine = model.getCenterY() - model.getHeight() / 4 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                        canvas.drawText(day, textStart, baseLine, mDayPaint);
                    } else if (model.getDayState() == DayState.STATE_SELECTED) {
                        //选中状态
                        //画选中背景
                        Paint bgPaint = new Paint();
                        bgPaint.setColor(mEnablePriceColor);
                        canvas.drawRect(model.getCenterX() - model.getWidth() / 2,
                                model.getCenterY() - model.getHeight() / 2,
                                model.getCenterX() + model.getWidth() / 2,
                                model.getCenterY() + model.getHeight() / 2,
                                bgPaint);

                        //画价格
                        mPricePaint.setColor(mSelectColor);
                        Rect priceTextBound = new Rect();
                        mPricePaint.getTextBounds(price, 0, price.length(), priceTextBound);
                        float priceStart = model.getCenterX() - priceTextBound.width() / 2;
                        Paint.FontMetrics priceMetrics = mWeekPaint.getFontMetrics();
                        float priceLine = model.getCenterY() + model.getHeight() / 4 + (Math.abs(priceMetrics.ascent) - priceMetrics.descent) / 2;
                        canvas.drawText(price, priceStart, priceLine, mPricePaint);

                        //画日期
                        mDayPaint.setColor(mSelectColor);
                        Rect dayTextBound = new Rect();
                        mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                        float textStart = model.getCenterX() - dayTextBound.width() / 2;
                        Paint.FontMetrics fontMetrics = mWeekPaint.getFontMetrics();
                        float baseLine = model.getCenterY() - model.getHeight() / 4 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                        canvas.drawText(day, textStart, baseLine, mDayPaint);
                    }
                }
            }
        }
    }


    /**
     * 初始化日期列表数据
     */
    private void initDayList() {
        int day = 0;
        for (int i = 0; i < mDayList.length; i++) {
            for (int j = 0; j < mDayList[i].length; j++) {
                day++;
                if (mEnableDays.containsKey(day)) {
                    DayState state = DayState.STATE_ENABLE;
                    if (mSelectedDays.containsKey(day)) {
                        state = DayState.STATE_SELECTED;
                    }

                    if (i == 0) {
                        mDayList[i][j] = new DayModel(mDayWidth / 2 + (j + firstDayWeekIndex) * mDayWidth,
                                mTitleHeight + mWeekHeight + mDayHeight / 2 + i * mDayHeight,
                                mDayWidth, mDayHeight, day, mEnableDays.get(day), state);
                    } else {
                        mDayList[i][j] = new DayModel(mDayWidth / 2 + j * mDayWidth,
                                mTitleHeight + mWeekHeight + mDayHeight / 2 + i * mDayHeight,
                                mDayWidth, mDayHeight, day, mEnableDays.get(day), state);
                    }
                } else {
                    if (i == 0) {
                        mDayList[i][j] = new DayModel(mDayWidth / 2 + (j + firstDayWeekIndex) * mDayWidth,
                                mTitleHeight + mWeekHeight + mDayHeight / 2 + i * mDayHeight,
                                mDayWidth, mDayHeight, day, 0, DayState.STATE_DISABLE);
                    } else {
                        mDayList[i][j] = new DayModel(mDayWidth / 2 + j * mDayWidth,
                                mTitleHeight + mWeekHeight + mDayHeight / 2 + i * mDayHeight,
                                mDayWidth, mDayHeight, day, 0, DayState.STATE_DISABLE);
                    }
                }
            }
        }
    }


}
