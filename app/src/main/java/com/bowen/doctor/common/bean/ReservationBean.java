package com.bowen.doctor.common.bean;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/20.
 */
public class ReservationBean {

    private String week;
    private String date;
    private long dateTime;
    private String[] appNumStr;
    private String[] dayTime;
    private int[] reserveStatus;
    private boolean isChoose;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String[] getDayTime() {
        return dayTime;
    }

    public void setDayTime(String[] dayTime) {
        this.dayTime = dayTime;
    }

    public int[] getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int[] reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String[] getAppNumStr() {
        return appNumStr;
    }

    public void setAppNumStr(String[] appNumStr) {
        this.appNumStr = appNumStr;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
