package com.bowen.doctor.common.event;

import com.bowen.commonlib.base.BaseEvent;
import com.bowen.doctor.common.bean.network.PrescriptionBean;

/**
 * Describe:添加常用方剂成功事件
 * Created by zhuzhipeng on 2018/6/26.
 */

public class EditCommonUsePrescSuccEvent extends BaseEvent {
    public PrescriptionBean mBean;
    public EditCommonUsePrescSuccEvent(){

    }
    public EditCommonUsePrescSuccEvent(PrescriptionBean bean) {
        mBean = bean;
    }
}