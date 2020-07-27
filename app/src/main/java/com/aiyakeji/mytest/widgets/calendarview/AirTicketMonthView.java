package com.aiyakeji.mytest.widgets.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.aiyakeji.mytest.widgets.calendarview.AirTicketMonthViewFragment.firstSelectedDay;
import static com.aiyakeji.mytest.widgets.calendarview.AirTicketMonthViewFragment.secondSelectedDay;


/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:机票自定义日历
 */
public class AirTicketMonthView extends View {
    private static final String TAG = "MonthView测试";

    private static final int WEEK_LENGTH = 7;
    //每行之间的间距
    private float lineDivideHeight = 10f;
    private float mWidth;
    private float mHeight;
    private float mDayWidth;
    private float mDayHeight;
    private float mShowHalfHeight;

    //日期
    private Paint mDayPaint;
    //状态，入住or离店
    private Paint mStatePaint;

    private int mDisableDayColor = Color.parseColor("#d6d6d6");
    private int mEnableDayColor = Color.parseColor("#333333");
    private int mSelectedBGColor = Color.parseColor("#c31f96");
    private int mSelectedCenterColor = Color.parseColor("#f6ddf0");

    private int mSelectTextColor = Color.WHITE;
    private String startDayLable = "去程";
    private String endDayLable = "返程";
    private String startEndDayLabel = "去/返";
    private int enableYear, enableMonth, enableDay;
    //该月的天数
    private int daysInMonth;
    //该月第一天是周几（0为周日）
    private int firstDayWeekIndex;
    //行数
    private int row;
    private MonthViewModel[][] mDayList;
    MonthViewModel model = null;
    float touchDownX, touchDownY;
    private List<MonthViewModel> mDayPriceList = new ArrayList<>();
    private OnDayClickListener mListener;
    private Paint mBgPaint;


    public AirTicketMonthView(Context context) {
        this(context, null);
    }

    public AirTicketMonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirTicketMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDayPaint = new Paint();
        mDayPaint.setAntiAlias(true);
        mDayPaint.setTextSize(40);
        mDayPaint.setColor(mDisableDayColor);

        mStatePaint = new Paint();
        mStatePaint.setAntiAlias(true);
        mStatePaint.setTextSize(30);
        mStatePaint.setColor(mSelectedBGColor);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);

//        setEnableDate(2018, 5, 18);
    }


    private void setEnableDate(int year, int month, int day) {
        enableYear = year;
        enableMonth = month;
        enableDay = day;

        String dateStr = DateTimeUtil.formatYearMonthDay1(enableYear + "年" + enableMonth + "月" + enableDay + "日");
        daysInMonth = DateTimeUtil.getDaysFromYearMonth(dateStr);

        String[] dateSplit = dateStr.split("-");
        String firstDay = dateSplit[0] + "-" + dateSplit[1] + "-01";
        firstDayWeekIndex = DateTimeUtil.formatDateGetWeekindex(firstDay);

        //补齐前面的天数，用来计算日期数组大小和绘图开始位置
        int tempDaysInMonth = daysInMonth + firstDayWeekIndex;

        //计算行数
        row = tempDaysInMonth / WEEK_LENGTH + (tempDaysInMonth % WEEK_LENGTH == 0 ? 0 : 1);

        mDayList = new MonthViewModel[row][];
        mDayList[0] = new MonthViewModel[WEEK_LENGTH - firstDayWeekIndex];

        //初始化日期数组大小
        if (tempDaysInMonth % WEEK_LENGTH == 0) {
            for (int i = 1; i < row; i++) {
                mDayList[i] = new MonthViewModel[WEEK_LENGTH];
            }
        } else {
            for (int i = 1; i < row - 1; i++) {
                mDayList[i] = new MonthViewModel[WEEK_LENGTH];
            }
            mDayList[row - 1] = new MonthViewModel[tempDaysInMonth % WEEK_LENGTH];
        }
    }


    public void setDayPriceList(List<MonthViewModel> list) {
        if (list == null) {
            return;
        }
        mDayPriceList.clear();
        mDayPriceList.addAll(list);
        MonthViewModel firstInListModel = list.get(0);
        setEnableDate(firstInListModel.year, firstInListModel.month, firstInListModel.day);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mDayWidth = mWidth / WEEK_LENGTH;
        mDayHeight = mHeight / row;

        mShowHalfHeight = mDayHeight / 2 - lineDivideHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initDayList();

        drawDays(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                touchDownY = event.getY();
                if (touchDownY > mDayHeight) {
                    float dis = touchDownY;
                    int dayRow, dayCol;
                    if (dis % mDayHeight != 0) {
                        dayRow = (int) Math.floor(dis / mDayHeight);
                    } else {
                        dayRow = (int) (dis / mDayHeight) - 1;
                    }

                    if (touchDownX % mDayWidth != 0) {
                        dayCol = (int) Math.floor(touchDownX / mDayWidth);
                    } else {
                        dayCol = (int) (touchDownX / mDayWidth) == 0 ? 0 : ((int) (touchDownX / mDayWidth) - 1);
                    }

                    if (dayRow < mDayList.length && dayCol < mDayList[dayRow].length) {
                        model = mDayList[dayRow][dayCol];
                    }
                } else if (touchDownY > 0 && touchDownY < mDayHeight &&
                        touchDownX > (firstDayWeekIndex * mDayWidth)) {
                    int dayCol;
                    float dis = touchDownX - firstDayWeekIndex * mDayWidth;
                    if (dis % mDayWidth != 0) {
                        dayCol = (int) Math.floor(dis / mDayWidth);
                    } else {
                        dayCol = (int) (dis / mDayWidth) - 1;
                    }

                    if (dayCol < mDayList[0].length) {
                        model = mDayList[0][dayCol];
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - touchDownX) > 30
                        || Math.abs(event.getY() - touchDownY) > 30) {
                    model = null;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (model != null && model.day >= enableDay) {
                    if (firstSelectedDay != null && secondSelectedDay == null) {
                        if (model.compare(firstSelectedDay) >= 0) {
                            model.state = DayState.STATE_SELECTED;
                            secondSelectedDay = model;
                        } else if (model.compare(firstSelectedDay) == -1) {
                            model.state = DayState.STATE_SELECTED;
                            firstSelectedDay = model;
                        }
                    } else {
                        model.state = DayState.STATE_SELECTED;
                        firstSelectedDay = model;
                        secondSelectedDay = null;
                    }

                    if (mListener != null) {
                        mListener.onDayClick(model);
                    }
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
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
                    MonthViewModel model = mDayList[i][j];
                    String day = model.day + "";
                    DayState state = model.state;

                    String topLabel = "";
                    if (model.compare(firstSelectedDay) == 0 && model.compare(secondSelectedDay) == 0) {
                        topLabel = startEndDayLabel;
                        state = DayState.STATE_SELECTED;
                    } else if (model.compare(firstSelectedDay) == 0) {
                        topLabel = startDayLable;
                        state = DayState.STATE_SELECTED;
                    } else if (model.compare(secondSelectedDay) == 0) {
                        topLabel = endDayLable;
                        state = DayState.STATE_SELECTED;
                    } else if (model.compare(firstSelectedDay) == 1 && model.compare(secondSelectedDay) == -1) {
                        topLabel = "";
                        state = DayState.STATE_SELECTED;
                    }

                    if (state == DayState.STATE_DISABLE) {
                        //不可选状态,画日期
                        mDayPaint.setColor(mDisableDayColor);
                        Rect dayTextBound = new Rect();
                        mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                        float textStart = model.centerX - dayTextBound.width() / 2;
                        Paint.FontMetrics fontMetrics = mDayPaint.getFontMetrics();
                        float baseLine = model.centerY + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                        canvas.drawText(day, textStart, baseLine, mDayPaint);
                    } else if (state == DayState.STATE_ENABLE) {
                        //可选状态,画日期
                        mDayPaint.setColor(mEnableDayColor);
                        Rect dayTextBound = new Rect();
                        mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                        float textStart = model.centerX - dayTextBound.width() / 2;
                        Paint.FontMetrics fontMetrics = mDayPaint.getFontMetrics();
                        float baseLine = model.centerY + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                        canvas.drawText(day, textStart, baseLine, mDayPaint);

                        //画价格
                        String drawPriceText = "¥" + model.price;
                        mStatePaint.setColor(mEnableDayColor);
                        Rect priceTextBound = new Rect();
                        mStatePaint.getTextBounds(drawPriceText, 0, drawPriceText.length(), priceTextBound);
                        float priceStart = model.centerX - priceTextBound.width() / 2;
                        Paint.FontMetrics priceMetrics = mStatePaint.getFontMetrics();
                        float priceLine = model.centerY + (model.height - dayTextBound.height()) / 4 + dayTextBound.height() / 2 + (Math.abs(priceMetrics.ascent) - priceMetrics.descent) / 2;
                        canvas.drawText(drawPriceText, priceStart, priceLine, mStatePaint);
                    } else if (state == DayState.STATE_SELECTED) {
                        //选中状态
                        //画背景
                        if (!TextUtils.isEmpty(topLabel)) {
                            if (topLabel.equals(startDayLable)) {
                                mBgPaint.setColor(mSelectedCenterColor);
                                canvas.drawRect(model.centerX, model.centerY - mShowHalfHeight, model.centerX + model.width / 2, model.centerY + mShowHalfHeight, mBgPaint);
                            } else if (topLabel.equals(endDayLable)) {
                                mBgPaint.setColor(mSelectedCenterColor);
                                canvas.drawRect(model.centerX - model.width / 2, model.centerY - mShowHalfHeight, model.centerX, model.centerY + mShowHalfHeight, mBgPaint);
                            }
                            mBgPaint.setColor(mSelectedBGColor);
                            canvas.drawRoundRect(new RectF(model.centerX - model.width / 2, model.centerY - mShowHalfHeight, model.centerX + model.width / 2, model.centerY + mShowHalfHeight), 10, 10, mBgPaint);
                        } else {
                            mBgPaint.setColor(mSelectedCenterColor);
                            canvas.drawRect(model.centerX - model.width / 2, model.centerY - mShowHalfHeight, model.centerX + model.width / 2, model.centerY + mShowHalfHeight, mBgPaint);
                        }


                        //画文字
                        if (!TextUtils.isEmpty(topLabel)) {
                            //画日期
                            mDayPaint.setColor(mSelectTextColor);
                            Rect dayTextBound = new Rect();
                            mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                            float textStart = model.centerX - dayTextBound.width() / 2;
                            Paint.FontMetrics fontMetrics = mDayPaint.getFontMetrics();
                            float baseLine = model.centerY + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                            canvas.drawText(day, textStart, baseLine, mDayPaint);

                            //画上标文字
                            mStatePaint.setColor(mSelectTextColor);
                            Rect labelTextBound = new Rect();
                            mStatePaint.getTextBounds(topLabel, 0, topLabel.length(), labelTextBound);
                            float labelStart = model.centerX - labelTextBound.width() / 2;
                            Paint.FontMetrics labelMetrics = mStatePaint.getFontMetrics();
                            float labelLine = model.centerY - (model.height - dayTextBound.height()) / 4 - dayTextBound.height() / 2 + (Math.abs(labelMetrics.ascent) - labelMetrics.descent) / 2;
                            canvas.drawText(topLabel, labelStart, labelLine, mStatePaint);

                            //画价格
                            String drawPriceText = "¥" + model.price;
                            mStatePaint.setColor(mSelectTextColor);
                            Rect priceTextBound = new Rect();
                            mStatePaint.getTextBounds(drawPriceText, 0, drawPriceText.length(), priceTextBound);
                            float priceStart = model.centerX - priceTextBound.width() / 2;
                            Paint.FontMetrics priceMetrics = mStatePaint.getFontMetrics();
                            float priceLine = model.centerY + (model.height - dayTextBound.height()) / 4 + dayTextBound.height() / 2 + (Math.abs(priceMetrics.ascent) - priceMetrics.descent) / 2;
                            canvas.drawText(drawPriceText, priceStart, priceLine, mStatePaint);
                        } else {
                            //中间选中状态,画日期
                            mDayPaint.setColor(mEnableDayColor);
                            Rect dayTextBound = new Rect();
                            mDayPaint.getTextBounds(day, 0, day.length(), dayTextBound);
                            float textStart = model.centerX - dayTextBound.width() / 2;
                            Paint.FontMetrics fontMetrics = mDayPaint.getFontMetrics();
                            float baseLine = model.centerY + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
                            canvas.drawText(day, textStart, baseLine, mDayPaint);

                            //画价格
                            String drawPriceText = "¥" + model.price;
                            mStatePaint.setColor(mEnableDayColor);
                            Rect priceTextBound = new Rect();
                            mStatePaint.getTextBounds(drawPriceText, 0, drawPriceText.length(), priceTextBound);
                            float priceStart = model.centerX - priceTextBound.width() / 2;
                            Paint.FontMetrics priceMetrics = mStatePaint.getFontMetrics();
                            float priceLine = model.centerY + (model.height - dayTextBound.height()) / 4 + dayTextBound.height() / 2 + (Math.abs(priceMetrics.ascent) - priceMetrics.descent) / 2;
                            canvas.drawText(drawPriceText, priceStart, priceLine, mStatePaint);
                        }
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
                DayState state;
                if (day < enableDay) {
                    state = DayState.STATE_DISABLE;
                } else {
                    state = DayState.STATE_ENABLE;
                }

                if (i == 0) {
                    mDayList[i][j] = new MonthViewModel(mDayWidth / 2 + (j + firstDayWeekIndex) * mDayWidth,
                            mDayHeight / 2,
                            mDayWidth, mDayHeight, enableYear, enableMonth, day, 0, state);
                } else {
                    mDayList[i][j] = new MonthViewModel(mDayWidth / 2 + j * mDayWidth,
                            mDayHeight / 2 + i * mDayHeight,
                            mDayWidth, mDayHeight, enableYear, enableMonth, day, 0, state);
                }
                if (mDayPriceList.size() > 0) {
                    for (MonthViewModel priceModel : mDayPriceList) {
                        if (mDayList[i][j].compare(priceModel) == 0) {
                            mDayList[i][j].price = priceModel.price;
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * 刷新
     */
    public void doRefush() {
        invalidate();
    }


    public interface OnDayClickListener {
        void onDayClick(MonthViewModel model);
    }


    public void setOnDayClickListener(OnDayClickListener listener) {
        mListener = listener;
    }

}
