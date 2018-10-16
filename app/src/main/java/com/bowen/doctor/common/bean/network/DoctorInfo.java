package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:医生信息
 * Created by zhuzhipeng on 2018/4/4.
 */

public class DoctorInfo implements Serializable {
    private static DoctorInfo instance = null;
    private String fileLink;
    private String name;
    private int sex;
    private String userPhone;
    private long birthdate;
    private String hospital;
    private String hospitalDepartments;
    private String positionStr;
    private String address;
    private int identify;
    private long authTime;
    private String introduction;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String diseases;
    private boolean isRecommended;
    private boolean isSet;//判断没有设置过图文咨询和门诊预约服务
    private boolean isData;

    public String getAppPicUrl() {
        return appPicUrl;
    }

    public void setAppPicUrl(String appPicUrl) {
        this.appPicUrl = appPicUrl;
    }

    private String appPicUrl;

    public static DoctorInfo getInstance() {
        if (instance == null) {
            synchronized (DoctorInfo.class) {
                if (instance == null) {
                    instance = new DoctorInfo();
                }
            }
        }
        return instance;
    }

    public static void setDoctorInstanceInfo(DoctorInfo bean) {
        if (instance == null) {
            synchronized (DoctorInfo.class) {
                if (instance == null) {
                    instance = new DoctorInfo();
                }
            }
        }
        if(instance!=null)
            instance = bean;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalDepartments() {
        return hospitalDepartments;
    }

    public void setHospitalDepartments(String hospitalDepartments) {
        this.hospitalDepartments = hospitalDepartments;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(long authTime) {
        this.authTime = authTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public boolean isRecommend() {
        return isRecommended;
    }

    public void setRecommend(boolean recommend) {
        this.isRecommended = recommend;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }
}
