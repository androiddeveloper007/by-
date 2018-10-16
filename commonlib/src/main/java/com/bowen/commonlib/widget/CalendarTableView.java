package com.bowen.commonlib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.bowen.commonlib.bean.DateNum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Describe:预约控件
 * Created by AwenZeng on 2018/6/26.
 */
public class CalendarTableView extends View {
    private Paint paint;
    private DisplayMetrics mDisplayMetrics;

    private Calendar mCalendar;
    //星期字体颜色  默认黑色
    private int mWeekdayColor = Color.parseColor("#000000");
    //未选中布局颜色  默认白色
    private int mUnSelectColor = Color.parseColor("#FFFFFF");
    //选中布局颜色  默认红色
    private int mSelectColor = Color.parseColor("#FF0000");

    private static final int NUM_COLUMNS = 8;
    private static final int NUM_ROWS = 4;

    //每一格的宽度和高度
    private int mColumnSize, mRowSize;
    //用户选中的行和列数
    private int checkRow, checkColumn;
    //控件的总宽度和总高度
    private int width, height;

    private String[] weekString = new String[]{ "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private List<DateNum> dateList = new ArrayList<>();
    private String[] timeString = new String[]{ "上午", "下午", "晚上"};

    public OnChooseReservateTimeListener mListener;

    public interface OnChooseReservateTimeListener{
        void chooseTime(DateNum dateNum, int time);
    }

    public void setmListener(OnChooseReservateTimeListener mListener) {
        this.mListener = mListener;
    }

    public CalendarTableView(Context context) {
        super(context);
        init();
    }

    public CalendarTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化列宽行高
     */
    private void init() {
        mDisplayMetrics = getResources().getDisplayMetrics();
        paint = new Paint();
        paint.setAntiAlias(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mDisplayMetrics.densityDpi * 30;
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mDisplayMetrics.densityDpi * 500;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        width = getWidth();
        height = getHeight();
        mColumnSize = width / NUM_COLUMNS;
        mRowSize = height / NUM_ROWS;

        //设置监测当前行数
        int row = 1;
        //行
        for (int j = 0; j < NUM_ROWS; j++) {

            //进行画线
            paint.setColor(Color.parseColor("#EDEDED"));
            paint.setStrokeWidth(4);
            canvas.drawLine(0, mRowSize * j, width, mRowSize * j, paint);

            //因为第一行有些特殊，单独拿出来绘制第一行
            if (j == 0) {
                //绘制列
                for (int i = 0; i < NUM_COLUMNS; i++) {
                    //进行画格子左边的竖线
                    paint.setColor(Color.parseColor("#EDEDED"));
                    paint.setStrokeWidth(4);
                    canvas.drawLine(mColumnSize * i, 0, mColumnSize * i, mRowSize, paint);

                    paint.setColor(Color.parseColor("#f5f5f5"));

                    //绘制背景色矩形
                    int startRecX = mColumnSize * i;
                    int startRecY = 0;
                    int endRecX = startRecX + mColumnSize;
                    int endRecY = mRowSize;

                    canvas.drawRect(startRecX, startRecY, endRecX, endRecY, paint);

                    //第一格不设置任何信息
                    if (i != 0) {
                        String text = weekString[i-1];
                        paint.setColor(mWeekdayColor);
                        paint.setTextSize(40);
                        int fontWidth = (int) paint.measureText(text);
                        //设置文字居中
                        int startX = mColumnSize * i + (mColumnSize - fontWidth) / 2;
                        int startY = mRowSize / 2;
                        //设置星期
                        canvas.drawText(text, startX, startY, paint);

                        //设置底部日期
                        paint.setTextSize(35);
                        paint.setColor(Color.parseColor("#6f6f6f"));
                        DateNum dateNum =  dateList.get(i-1);
                        String showDateStr =dateNum.getMonth()+"-"+dateNum.getDay();
                        //获取文字宽度
                        fontWidth = (int) paint.measureText(showDateStr);
                        //设置文字居中
                        startX = mColumnSize * i + (mColumnSize - fontWidth) / 2;
                        canvas.drawText(showDateStr, startX, startY + mRowSize / 3, paint);
                    }
                }
            } else {
                //绘制其他行
                //绘制列
                for (int i = 0; i < NUM_COLUMNS; i++) {

                    //进行画格子左边的竖线
                    paint.setColor(Color.parseColor("#EDEDED"));
                    paint.setStrokeWidth(4);
                    canvas.drawLine(mColumnSize * i, mRowSize * (row - 1), mColumnSize * i, mRowSize * row, paint);

                    if (checkRow != 0 && checkColumn != 0 && j == checkRow && i == checkColumn) {

                        paint.setColor(Color.parseColor("#FF0000"));
                        //绘制背景色矩形
                        int startRecX = mColumnSize * i;
                        int startRecY = mRowSize * (row - 1);
                        int endRecX = startRecX + mColumnSize;
                        int endRecY = startRecY + mRowSize;

                        //选中之后绘制文字
                        canvas.drawRect(startRecX, startRecY, endRecX, endRecY, paint);
                        paint.setTextSize(30);
                        paint.setColor(Color.parseColor("#FFFFFF"));
                        canvas.drawText("预约", (endRecX - startRecX) / 5 + startRecX, (endRecY - startRecY) / 2 + startRecY + 13, paint);


                    } else {
                        if(i!=0){
                            paint.setColor(Color.parseColor("#DCDCDC"));
                        }else{
                            paint.setColor(mUnSelectColor);
                        }
                        //绘制背景色白色矩形
                        int startRecX = mColumnSize * i;
                        int startRecY = mRowSize * (row - 1);
                        int endRecX = startRecX + mColumnSize;
                        int endRecY = startRecY + mRowSize;

                        canvas.drawRect(startRecX, startRecY, endRecX, endRecY, paint);
                        paint.setTextSize(30);
                        paint.setColor(Color.parseColor("#FFFFFF"));

                        canvas.drawText("不接诊", (endRecX - startRecX) / 5 + startRecX, (endRecY - startRecY) / 2 + startRecY + 13, paint);


                    }
                }
                paint.setTextSize(40);
                paint.setColor(mWeekdayColor);
                String text = timeString[j-1];
                int fontWidth = (int) paint.measureText(text);
                //设置文字居中
                int startX = (mColumnSize - fontWidth) / 2;
                int startY = (int) (mRowSize / 2 - (paint.ascent() + paint.descent()) / 2);
                canvas.drawText(text, startX, startY + mRowSize * j, paint);

            }
            //绘制完一行之后行数加1
            row++;
        }

        //进行画边界线
        paint.setColor(Color.parseColor("#44cdc3"));
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(0, height, width, height, paint);
    }


    //将日期列表传递过来
    public void setDateList(List<DateNum> dateList){
        this.dateList = dateList;
        //通知刷新
        invalidate();
    }

    //用来获取用户点击屏幕的坐标
    private int downX = 0, downY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {//点击事件
                    performClick();
                    doClickAction((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }

    /**
     * 执行点击事件
     *
     * @param x
     * @param y
     */
    private void doClickAction(int x, int y) {
        int row = y / mRowSize;
        int column = x / mColumnSize;

        if (row != 0 && column != 0) {
            //保存用户选中的item
            setCheckedItem(row, column);

        }
        invalidate();
    }

    private void setCheckedItem(int checkRow, int checkColumn) {
        this.checkRow = checkRow;
        this.checkColumn = checkColumn;

        DateNum dateNum = dateList.get(checkColumn-1);
        if(mListener!=null){
            mListener.chooseTime(dateNum,checkRow);
        }
    }

    /**
     * 计算当前一周日期并显示在界面
     */
    public void caculateWeekDates(int week) {
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mCalendar.add(Calendar.WEEK_OF_YEAR, week);
        Date date = mCalendar.getTime();
        this.dateList = dateToWeek(date);
        invalidate();
    }


    /**
     * 根据日期获得所在周的日期列表
     *
     * @param mDate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static List<DateNum> dateToWeek(Date mDate) {
        int b = mDate.getDay();
        Date fDate;
        List<DateNum> list = new ArrayList<>();
        Long fTime = mDate.getTime() - b * 24 * 3600000;
        for (int i = 0; i < 7; i++) {
            fDate = new Date();
            fDate.setTime(fTime + ((i + 1) * 24 * 3600000)); //一周从周日开始算
            DateNum dateNum = new DateNum();
            dateNum.setMonth(fDate.getMonth()+1);
            dateNum.setDay(fDate.getDate());
            list.add(dateNum);
        }
        return list;
    }

}