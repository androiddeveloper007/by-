package com.bowen.doctor.common.bean.network;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Describe:图文资讯设置
 * Created by zhuzhipeng on 2018/4/4.
 */

public class GraphicConsultSetBean implements Serializable {
    private String doctorId;
    private int askSwitch;
    private int consultDays;
    private double consultFee;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public int getAskSwitchStr() {
        return askSwitch;
    }

    public void setAskSwitchStr(int askSwitch) {
        this.askSwitch = askSwitch;
    }

    public int getConsultDays() {
        return consultDays;
    }

    public void setConsultDays(int consultDays) {
        this.consultDays = consultDays;
    }

    public double getConsultFee() {
        return consultFee;
    }

    public void setConsultFee(double consultFee) {
        this.consultFee = consultFee;
    }
}
