package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:门诊预约
 * Created by zhuzhipeng on 2018/4/4.
 */

public class EmrDoctor implements Serializable {
    private String doctorId;
    private String serviceSwitchStr;
    private String registerFee;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getServiceSwitchStr() {
        return serviceSwitchStr;
    }

    public void setServiceSwitchStr(String serviceSwitchStr) {
        this.serviceSwitchStr = serviceSwitchStr;
    }

    public String getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(String registerFee) {
        this.registerFee = registerFee;
    }
}
