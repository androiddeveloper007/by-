package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 * 我的医馆数据bean
 */
public class SearchHospitalRecord implements Serializable {
    private List<EmrHospitalBean> emrHospitalList;
    private Page page;

    public List<EmrHospitalBean> getEmrHospitalList() {
        return emrHospitalList;
    }

    public void setEmrHospitalList(List<EmrHospitalBean> emrHospitalList) {
        this.emrHospitalList = emrHospitalList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
