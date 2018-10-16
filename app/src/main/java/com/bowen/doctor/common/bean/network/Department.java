package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:科室
 * Created by AwenZeng on 2018/7/9.
 */
public class Department implements Serializable{
    private String departmentsId;
    private String departmentsName;
    private String hospitalDeptId;

    public String getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(String departmentsId) {
        this.departmentsId = departmentsId;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }

    public String getHospitalDeptId() {
        return hospitalDeptId;
    }

    public void setHospitalDeptId(String hospitalDeptId) {
        this.hospitalDeptId = hospitalDeptId;
    }
}
