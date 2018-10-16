package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Describe:我入驻的医馆
 * Created by zzp on 2018/8/4.
 */

public class MyApplyHospitalRecord implements Serializable {

    private ArrayList<ApplyHospital> applyList;
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<ApplyHospital> getApplyList() {
        return applyList;
    }

    public void setApplyList(ArrayList<ApplyHospital> applyList) {
        this.applyList = applyList;
    }
}
