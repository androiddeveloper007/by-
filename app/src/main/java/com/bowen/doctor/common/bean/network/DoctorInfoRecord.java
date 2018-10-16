package com.bowen.doctor.common.bean.network;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:医生信息
 * Created by zhuzhipeng on 2018/4/4.
 */

public class DoctorInfoRecord implements Serializable {
    private DoctorInfo emrDoctor;
    private String clinic;//入住医馆
    private String clinicDep;//入住医馆科室

    public DoctorInfo getEmrDoctor() {
        return emrDoctor;
    }

    public void setEmrDoctor(DoctorInfo emrDoctor) {
        this.emrDoctor = emrDoctor;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getClinicDep() {
        return clinicDep;
    }

    public void setClinicDep(String clinicDep) {
        this.clinicDep = clinicDep;
    }

}
