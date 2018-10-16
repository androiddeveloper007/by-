package com.bowen.doctor.common.bean.network;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/9.
 */
public class EmrDoctorListBean {
    /**
     * doctorId :
     * name :
     * headImgUrl :
     * hospital :
     * positionStr :
     * hospitalDepartments :
     * diseases :
     * introduction :
     */

    private String doctorId;
    private String name;
    private String headImgUrl;
    private String hospital;
    private String positionStr;
    private String hospitalDepartments;
    private String diseases;
    private String introduction;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getHospitalDepartments() {
        return hospitalDepartments;
    }

    public void setHospitalDepartments(String hospitalDepartments) {
        this.hospitalDepartments = hospitalDepartments;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}