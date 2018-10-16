package com.bowen.doctor.common.bean.network;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/20.
 */
public class SitClinic {
    private String hospital;
    private String address;
    private boolean isChoose;

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
