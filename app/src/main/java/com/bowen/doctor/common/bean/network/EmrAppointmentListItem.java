package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:门诊预约
 * Created by zhuzhipeng on 2018/4/4.
 */

public class EmrAppointmentListItem implements Serializable {
    private String appointmentId;
    private String hospital;
    private String appointmentDate;
    private String typeStr;
    private String typeName;
    private String maxNum;
    private String week;
    private String weekStatusStr;
    private String isCureStr;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeekStatusStr() {
        return weekStatusStr;
    }

    public void setWeekStatusStr(String weekStatusStr) {
        this.weekStatusStr = weekStatusStr;
    }

    public String getIsCureStr() {
        return isCureStr;
    }

    public void setIsCureStr(String isCureStr) {
        this.isCureStr = isCureStr;
    }
}
