package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Created by zzp on 2018/5/15.
 * 我的医馆数据bean
 */
public class MyEnterHospitalBean implements Serializable {
    private String hospitalName;
    private String hospitalId;
    private String authStatusStr;
    private String authStatus;
    private String addressStr;
    private long createTime;
    private String doctorId;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
    private String address;
    private String phone;
    private String introduction;
    private String hospitalImgUrl;
    private String licenseImgUrl;
    private String certificateImgUrl;
    private String departmentsName;
    private String authRemark;
    private String doctorHospitalDeptId;
    private String doctorImgUrl;
    private String doctorName;
    private String positionStr;

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

    public String getAuthStatusStr() {
        return authStatusStr;
    }

    public void setAuthStatusStr(String authStatusStr) {
        this.authStatusStr = authStatusStr;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHospitalImgUrl() {
        return hospitalImgUrl;
    }

    public void setHospitalImgUrl(String hospitalImgUrl) {
        this.hospitalImgUrl = hospitalImgUrl;
    }

    public String getLicenseImgUrl() {
        return licenseImgUrl;
    }

    public void setLicenseImgUrl(String licenseImgUrl) {
        this.licenseImgUrl = licenseImgUrl;
    }

    public String getCertificateImgUrl() {
        return certificateImgUrl;
    }

    public void setCertificateImgUrl(String certificateImgUrl) {
        this.certificateImgUrl = certificateImgUrl;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public String getDoctorHospitalDeptId() {
        return doctorHospitalDeptId;
    }

    public void setDoctorHospitalDeptId(String doctorHospitalDeptId) {
        this.doctorHospitalDeptId = doctorHospitalDeptId;
    }

    public String getDoctorImgUrl() {
        return doctorImgUrl;
    }

    public void setDoctorImgUrl(String doctorImgUrl) {
        this.doctorImgUrl = doctorImgUrl;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }
}
