package com.bowen.doctor.common.bean;

/**
 * Describe:
 * Created by AwenZeng on 2018/6/26.
 */
public class DateNum {
    private int dayType;
    private String dayTypeStr;
    private long dateTime;
    private String week;
    private int row;
    private int column;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getDayType() {
        return dayType;
    }

    public void setDayType(int dayType) {
        this.dayType = dayType;
    }

    public String getDayTypeStr() {
        return dayTypeStr;
    }

    public void setDayTypeStr(String dayTypeStr) {
        this.dayTypeStr = dayTypeStr;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
