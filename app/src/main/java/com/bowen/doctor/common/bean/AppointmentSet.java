package com.bowen.doctor.common.bean;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/18.
 */
public class AppointmentSet {
    private long date;
    private int dayType;
    private int isReception;
    private String clinicName;
    private String clinicAddress;
    private String peopleCount;
    private String appointmentId;
    private String dayTypeStr;
    private String typeName;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDayType() {
        return dayType;
    }

    public void setDayType(int dayType) {
        this.dayType = dayType;
    }

    public int getIsReception() {
        return isReception;
    }

    public void setIsReception(int isReception) {
        this.isReception = isReception;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDayTypeStr() {
        return dayTypeStr;
    }

    public void setDayTypeStr(String dayTypeStr) {
        this.dayTypeStr = dayTypeStr;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
