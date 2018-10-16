package com.bowen.doctor.common.bean.network;

import java.util.ArrayList;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/17.
 */
public class AppointmentPeriodInfo {

    private ArrayList<AppointmentPeriod>  emrAppointmentList;
    private String hospital;

    public ArrayList<AppointmentPeriod> getEmrAppointmentList() {
        return emrAppointmentList;
    }

    public void setEmrAppointmentList(ArrayList<AppointmentPeriod> emrAppointmentList) {
        this.emrAppointmentList = emrAppointmentList;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
