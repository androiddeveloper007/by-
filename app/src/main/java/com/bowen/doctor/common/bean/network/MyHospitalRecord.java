package com.bowen.doctor.common.bean.network;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 * 我的医馆数据bean
 */
public class MyHospitalRecord implements Serializable {
    private MyEnterHospitalBean emrHospital;
    private List<Department> deptList;

    public MyEnterHospitalBean getEmrHospital() {
        return emrHospital;
    }

    public void setEmrHospital(MyEnterHospitalBean emrHospital) {
        this.emrHospital = emrHospital;
    }

    public List<Department> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }
}
