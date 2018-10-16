package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Describe:我的咨询
 * Created by zhuzhipeng on 2018/7/9
 */

public class ConsultInfo implements Serializable{

    private ArrayList<ConsultItem> consultList;
    private Page page;

    public ArrayList<ConsultItem> getConsultList() {
        return consultList;
    }

    public void setConsultList(ArrayList<ConsultItem> list) {
        this.consultList = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
