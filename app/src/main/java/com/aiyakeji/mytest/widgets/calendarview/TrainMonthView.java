package com.aiyakeji.mytest.widgets.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aiyakeji.mytest.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:自定义日历
 */
public class TrainMonthView extends View {
    private static final String TAG = "MonthView测试";

    private static final int WEEK_LENGTH = 7;
    //每行之间的间距
    private float lineDivideHeight = 10f;
    private float mWidth;
    private float mHeight;
    private float mDayWidth;
    private float mDayHeight = 250f;
    private float mShowHalfHeight;
    private int mDayTextSize = 46;
    private int mTopTextSize = 30;
    private int mDownTextSize = 30;


    //日期
    private Paint mDayPaint;
    //状态，入住or离店
    private Paint mStatePaint;

    private int mDisableColor = Color.parseColor("#999999");
    private int mEnableColor = Color.parseColor("#F19725");
    private int mSelectedBGColor = Color.parseColor("#c31f96");
    private int mSelectTextColor = Color.WHITE;

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
    private Rect textBound = new Rect();
    private boolean initDrawList = false;
    private RectF bgRectf;
    private LinearGradient bgLinearGradient;
    private MonthViewModel todayModel;


    public TrainMonthView(Context context) {
        this(context, null);
    }

    public TrainMonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrainMonthView);
        mDayTextSize = typedArray.getDimensionPixelSize(R.styleable.TrainMonthView_tmvDayTextSize,46);
        mTopTextSize = typedArray.getDimensionPixelSize(R.styleable.TrainMonthView_tmvTopTextSize,30);
        mDownTextSize = typedArray.getDimensionPixelSize(R.styleable.TrainMonthView_tmvDownTextSize,30);
        mDayHeight = typedArray.getDimensionPixelSize(R.styleable.TrainMonthView_tmvDayHeight,250);
        lineDivideHeight = typedArray.getDimension(R.styleable.TrainMonthView_tmvVerticalDaySpace,10f);
        typedArray.recycle();

        init();
    }

    private void init() {
        mDayPaint = new Paint();
        mDayPaint.setAntiAlias(true);
        mDayPaint.setTextSize(mDayTextSize);
        mDayPaint.setColor(mEnableColor);

        mStatePaint = new Paint();
        mStatePaint.setAntiAlias(true);
        mStatePaint.setTextSize(mTopTextSize);
        mStatePaint.setColor(mDisableColor);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        bgLinearGradient = new LinearGradient(0, 0, 0, mDayHeight, Color.parseColor("#F3478C"), Color.parseColor("#f871bc"), Shader.TileMode.CLAMP);
//        bgLinearGradient = new LinearGradient(0, 0, 0, mDayHeight, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        mBgPaint.setShader(bgLinearGradient);

        todayModel = new MonthViewModel(DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());

//        setEnableDate(todayModel.year, todayModel.month, todayModel.day);
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


    private void setEnableDate(int year, int month, int day) {
        String dateStr = DateTimeUtil.formatYearMonthDay1(year + "年" + month + "月" + day + "日");
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightModel == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (mDayHeight * row + lineDivideHeight * (row - 1));
        }
        setMeasuredDimension(widthSize, height);

        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight();

        mDayWidth = mWidth / WEEK_LENGTH;

        mShowHalfHeight = mDayHeight / 2 - lineDivideHeight;

        initDayList();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
                if (model != null && model.state != DayState.STATE_DISABLE) {
                    TrainMonthViewFragment.selectedDay = model;

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

                    if (model.compare(TrainMonthViewFragment.selectedDay) == 0) {
                        state = DayState.STATE_SELECTED;
                    }

                    if (state == DayState.STATE_DISABLE) {
                        mDayPaint.setColor(mDisableColor);
                        mStatePaint.setColor(mDisableColor);
                    } else if (state == DayState.STATE_ENABLE) {
                        mDayPaint.setColor(mEnableColor);
                        mStatePaint.setColor(mDisableColor);
                    } else if (state == DayState.STATE_SELECTED) {
                        mDayPaint.setColor(mSelectTextColor);
                        mStatePaint.setColor(mSelectTextColor);

                        //画背景
                        bgRectf = new RectF(model.centerX - model.width / 2, model.centerY - mShowHalfHeight, model.centerX + model.width / 2, model.centerY + mShowHalfHeight);
                        canvas.drawRoundRect(bgRectf, 20, 20, mBgPaint);
                    }


                    //画日期
                    if (todayModel.compare(model) == 0) {
                        day = "今天";
                    }
                    mDayPaint.getTextBounds(day, 0, day.length(), textBound);
                    float textStart = model.centerX - textBound.width() / 2;
                    int dayDy = (textBound.bottom - textBound.top) / 2 - textBound.bottom;
                    float baseLine = model.centerY + dayDy;
                    canvas.drawText(day, textStart, baseLine, mDayPaint);

                    //画上标文字
                    if (!TextUtils.isEmpty(model.topMsg)) {
                        mStatePaint.setTextSize(mTopTextSize);
                        mStatePaint.getTextBounds(model.topMsg, 0, model.topMsg.length(), textBound);
                        float labelStart = model.centerX - textBound.width() / 2;
                        int dy = (textBound.bottom - textBound.top) / 2 - textBound.bottom;
                        float labelLine = model.centerY - mDayHeight / 4 + dy;
                        canvas.drawText(model.topMsg, labelStart, labelLine, mStatePaint);
                    }

                    //画下标
                    if (!TextUtils.isEmpty(model.downMsg)) {
                        mStatePaint.setTextSize(mDownTextSize);
                        mStatePaint.getTextBounds(model.downMsg, 0, model.downMsg.length(), textBound);
                        float priceStart = model.centerX - textBound.width() / 2;
                        int downDy = (textBound.bottom - textBound.top) / 2 - textBound.bottom;
                        float priceLine = model.centerY + mDayHeight / 4 + downDy;
                        canvas.drawText(model.downMsg, priceStart, priceLine, mStatePaint);
                    }
                }
            }
        }
    }


    /**
     * 初始化日期列表数据
     */
    private void initDayList() {
        if (initDrawList) {
            return;
        }
        initDrawList = true;
        int day = 0;
        for (int i = 0; i < mDayList.length; i++) {
            for (int j = 0; j < mDayList[i].length; j++) {
                day++;
                for (MonthViewModel dayModel : mDayPriceList) {
                    if (dayModel.day == day) {
                        if (i == 0) {
                            mDayList[i][j] = new MonthViewModel(mDayWidth / 2 + (j + firstDayWeekIndex) * mDayWidth + getPaddingLeft(),
                                    mDayHeight / 2,
                                    mDayWidth, mDayHeight, dayModel.year, dayModel.month, day, 0, dayModel.state);
                        } else {
                            mDayList[i][j] = new MonthViewModel(mDayWidth / 2 + j * mDayWidth + getPaddingLeft(),
                                    mDayHeight / 2 + i * mDayHeight,
                                    mDayWidth, mDayHeight, dayModel.year, dayModel.month, day, 0, dayModel.state);
                        }
                        mDayList[i][j].downMsg = dayModel.downMsg;
                        mDayList[i][j].topMsg = dayModel.topMsg;
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
