package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 * 粉丝的bean
 */

public class EmrDoctorFanRecord implements Serializable {
    private List<EmrDoctorFan> emrDoctorFan;

    public List<EmrDoctorFan> getFansList() {
        return emrDoctorFan;
    }

    public void setFansList(List<EmrDoctorFan> fansList) {
        this.emrDoctorFan = fansList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    private Page page;
}
