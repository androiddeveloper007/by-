package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Created by zzp on 2018/5/15.
 * 我入驻的医馆
 */
public class ApplyHospital implements Serializable {
    private String hospitalId;
    private String doctorHospitalDeptId;
    private String hospitalName;
    private String departmentsName;
    private String authStatusStr;
    private String createTime;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDoctorHospitalDeptId() {
        return doctorHospitalDeptId;
    }

    public void setDoctorHospitalDeptId(String doctorHospitalDeptId) {
        this.doctorHospitalDeptId = doctorHospitalDeptId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }

    public String getAuthStatusStr() {
        return authStatusStr;
    }

    public void setAuthStatusStr(String authStatusStr) {
        this.authStatusStr = authStatusStr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
