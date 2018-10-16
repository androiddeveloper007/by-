package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Created by zzp on 2018/5/15.
 * 我的医馆的bean
 */

public class MyHospital implements Serializable {
    private String hospitalName;
    private String hospitalId;
    private String phone;
    private String addressStr;
    private String hospitalImgUrl;
    private String distance;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getHospitalImgUrl() {
        return hospitalImgUrl;
    }

    public void setHospitalImgUrl(String hospitalImgUrl) {
        this.hospitalImgUrl = hospitalImgUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
