package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 * 我的医馆数据bean
 */
public class MyEnterAuditRecord implements Serializable {
    private List<MyEnterHospitalBean> applyList;
    private Page page;

    public List<MyEnterHospitalBean> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<MyEnterHospitalBean> applyList) {
        this.applyList = applyList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
