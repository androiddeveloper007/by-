package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.util.ArrayList;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/19.
 */
public class AppointmentUserInfo {

    private ArrayList<AppointmentUser>  voList;

    private Page page;

    public ArrayList<AppointmentUser> getVoList() {
        return voList;
    }

    public void setVoList(ArrayList<AppointmentUser> voList) {
        this.voList = voList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
