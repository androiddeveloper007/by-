package com.bowen.doctor.common.bean.network;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Describe:门诊预约
 * Created by zhuzhipeng on 2018/4/4.
 */

public class OutpatientSetRecord implements Serializable {

    private String doctorId;
    private int serviceSwitch;
    private String registerFee;

    private ArrayList<AppointmentInfo> emrAppointmentList;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public int getServiceSwitch() {
        return serviceSwitch;
    }

    public void setServiceSwitch(int serviceSwitch) {
        this.serviceSwitch = serviceSwitch;
    }

    public String getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(String registerFee) {
        this.registerFee = registerFee;
    }

    public ArrayList<AppointmentInfo> getEmrAppointmentList() {
        return emrAppointmentList;
    }

    public void setEmrAppointmentList(ArrayList<AppointmentInfo> emrAppointmentList) {
        this.emrAppointmentList = emrAppointmentList;
    }
}
