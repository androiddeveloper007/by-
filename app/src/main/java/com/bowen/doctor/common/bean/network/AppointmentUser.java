package com.bowen.doctor.common.bean.network;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/19.
 */
public class AppointmentUser {


    /**
     * appointmentId :
     * appointmentDate :
     * week :
     * typeName :
     * appointmentOrderId :
     * userId :
     * appointmentStatus :
     * fileLink :
     * userNickname :
     */

    private String appointmentId;
    private String appointmentDate;
    private String week;
    private String typeName;
    private String appointmentOrderId;
    private String userId;
    private int appointmentStatus;
    private String fileLink;
    private String userNickname;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAppointmentOrderId() {
        return appointmentOrderId;
    }

    public void setAppointmentOrderId(String appointmentOrderId) {
        this.appointmentOrderId = appointmentOrderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(int appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
