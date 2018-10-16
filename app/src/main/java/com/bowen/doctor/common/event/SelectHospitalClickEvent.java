package com.bowen.doctor.common.event;

import com.bowen.commonlib.base.BaseEvent;
import com.bowen.doctor.common.bean.network.EmrHospitalBean;

/**
 * Describe:选择医馆点击事件
 * Created by zhuzhipeng on 2018/8/6.
 */

public class SelectHospitalClickEvent extends BaseEvent {
    private EmrHospitalBean bean;
    public SelectHospitalClickEvent(){}
    public SelectHospitalClickEvent(EmrHospitalBean bean){this.bean = bean;}
    public EmrHospitalBean getHospitalBean(){
        return this.bean;
    }
}